package com.DLBCSPSE01.referentx.service;

import com.DLBCSPSE01.referentx.model.*;
import com.DLBCSPSE01.referentx.repository.SourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SourceService {

    private final SourceRepository sourceRepository;

    public SourceService(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }

    public BaseSource addNew(BaseSource source) {
        return sourceRepository.save(source);
    }

    public BaseSource edit(BaseSource source) {
        return sourceRepository.save(source);
    }

    public List<BaseSource> getByProject(Project project) {
        return sourceRepository.findByProject(project);
    }

    public BaseSource getOne(int id) {
        return sourceRepository.findById(id).orElseThrow(() -> new RuntimeException("Source not found"));
    }

    public void delete(BaseSource source) {
        sourceRepository.delete(source);
    }
}