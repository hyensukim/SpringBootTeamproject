$(document).ready(function() {
    // 초기 로딩 시 첫 페이지 내용 불러오기
    loadContent(1);

    // 페이징 처리된 콘텐츠 로드 함수
    function loadContent(page) {
        $.ajax({
            url: '/admin/post/postList/all',  // 서버에서 데이터를 가져올 URL
            data: { page: page },  // 현재 페이지 번호 전달
            success: function(response) {
                $('#content').html(response);  // 콘텐츠 업데이트

                let totalCnt = response.totalCnt;  // 총 게시글 수
                let pageSize = response.pageSize;  // 한 페이지당 게시글 수

                let totalPages = Math.ceil(totalCnt / pageSize);  // 전체 페이지 수 계산

                $('#pagination').empty();  // 기존 페이징 링크 삭제

                for(let i = 1; i <= totalPages; i++) {
                    let link = $('<a>').attr('href', '#').text(i);

                    link.click(function(e) {
                        e.preventDefault();
                        loadContent(i);
                    });

                    $('#pagination').append(link);
                }
            },
            error: function(xhr, status, error) {
                alert("페이지 로딩에 실패하였습니다.");
            }
        });
    }
});