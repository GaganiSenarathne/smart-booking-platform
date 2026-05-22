package com.gagani.smart_booking_platform.service.impl;

import com.gagani.smart_booking_platform.dto.request.ResourceRequestDTO;
import com.gagani.smart_booking_platform.dto.response.ResourceResponseDTO;
import com.gagani.smart_booking_platform.entity.Resource;
import com.gagani.smart_booking_platform.entity.UserInfo;
import com.gagani.smart_booking_platform.exception.DuplicateResourceException;
import com.gagani.smart_booking_platform.exception.InvalidBookingException;
import com.gagani.smart_booking_platform.exception.ResourceNotFoundException;
import com.gagani.smart_booking_platform.repository.ResourceRepository;
import com.gagani.smart_booking_platform.repository.UserInfoRepository;
import com.gagani.smart_booking_platform.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final UserInfoRepository userInfoRepository;


    @Override
    public ResourceResponseDTO createResource(ResourceRequestDTO resourceRequestDTO, String email) {

        UserInfo user = userInfoRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException(
                        String.format("User not found with email: %s", email)
        ));

        Resource resource = resourceRepository.findById(resourceRequestDTO.getId()).orElseThrow(() -> new ResourceNotFoundException(String.format("Resource already found BY the ID: %s", resourceRequestDTO.getId())));

        Resource resource1 = resourceRepository.findByName(resourceRequestDTO.getName());

        if (resource1 != null) {
            throw new DuplicateResourceException("Resource already exists!");
        }

        resource.setName(resourceRequestDTO.getName());
        resource.setDescription(resourceRequestDTO.getDescription());
        resource.setCreatedBy(user);
        Resource saved = resourceRepository.save(resource);

        return mapToResourceTO(saved);
    }

    @Override
    public ResourceResponseDTO updateResource(Long id, ResourceRequestDTO resourceRequestDTO){

        Resource resource = resourceRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Resource does not exist!"));

        resource.setName(resourceRequestDTO.getName());
        resource.setActive(resourceRequestDTO.isActive());
        resource.setDescription(resourceRequestDTO.getDescription());
        resource.setType(resourceRequestDTO.getType());
        Resource saved = resourceRepository.save(resource);

        return mapToResourceTO(saved);
    }

    @Override
    public ResourceResponseDTO getResourceById(Long id) {

        Resource resource = resourceRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Resource does not exist!"));

        return mapToResourceTO(resource);
    }


    @Override
    public Page<ResourceResponseDTO> getAllResources(Pageable pageable) {

        Page<Resource> resources = resourceRepository.findAll(pageable);
        return resources.map(this::mapToResourceTO);
    }

    @Override
    public void deleteResource(Long id) {

        Optional<Resource> resourceOptional = resourceRepository.findById(id);
        if (resourceOptional.isPresent()) {
            resourceRepository.deleteById(id);
        }else {
            throw new ResourceNotFoundException("Resource does not exist!");
        }
    }

    @Override
    public List<ResourceResponseDTO> getAvailableResources(Instant start, Instant end) {

        if(!start.isBefore(end)) {
            throw new InvalidBookingException("Invalid start date!");

        }

        List<Resource> resources = resourceRepository.findAvailableResources(start, end);

        return resources.stream().map(this::mapToResourceTO).toList();
    }

    public ResourceResponseDTO mapToResourceTO(Resource resource) {

        ResourceResponseDTO resourceResponseDTO = new ResourceResponseDTO();
        resourceResponseDTO.setId(resource.getId());
        resourceResponseDTO.setName(resource.getName());
        resourceResponseDTO.setDescription(resource.getDescription());
        resourceResponseDTO.setType(resourceResponseDTO.getType());
        resourceResponseDTO.setActive(resource.isActive());

        return resourceResponseDTO;
    }

}
