package com.springboot.shootformoney.file;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FileRepository {

    private final EntityManager em;

    public void save(File file) {
        if (file.getId() == null) {
            em.persist(file);
        } else {
            em.merge(file);
        }
    }

    public File findOne(Long id){
        return em.find(File.class, id);
    }

    public List<File> findAll() {
        return em.createQuery("select f from File f", File.class)
                .getResultList();
    }

}