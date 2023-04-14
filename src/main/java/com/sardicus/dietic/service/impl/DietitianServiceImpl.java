package com.sardicus.dietic.service.impl;


import com.sardicus.dietic.dto.DietitianDto;
import com.sardicus.dietic.entity.Dietitian;
import com.sardicus.dietic.exception.ResourceNotFoundException;
import com.sardicus.dietic.repo.DietitianRepo;
import com.sardicus.dietic.service.DietitianService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DietitianServiceImpl implements DietitianService {
    private final DietitianRepo dietitianRepo;

    private final ModelMapper mapper;


    public DietitianDto getDietitianById(int id) {
        Dietitian dietitian = dietitianRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(dietitian);
    }


    // convert Entity into DTO
    private DietitianDto mapToDTO(Dietitian dietitian){
        DietitianDto dietitianDto = mapper.map(dietitian, DietitianDto.class);
        return dietitianDto;
    }

    // convert DTO to entity
    private Dietitian mapToEntity(DietitianDto companyDto){
        Dietitian dietitian = mapper.map(companyDto, Dietitian.class);
        return dietitian;
    }
}
