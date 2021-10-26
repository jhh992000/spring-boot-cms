package me.hhjeong.springbootcms.common.security.application;

import static me.hhjeong.springbootcms.common.base.BaseConstants.PAGE_SIZE;
import static me.hhjeong.springbootcms.common.base.BaseConstants.START_PAGE_NO;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.hhjeong.springbootcms.common.security.domain.Resources;
import me.hhjeong.springbootcms.common.security.domain.ResourcesRepository;
import me.hhjeong.springbootcms.common.security.dto.CreateResourcesRequest;
import me.hhjeong.springbootcms.common.security.dto.ResourcesResponse;
import me.hhjeong.springbootcms.common.security.dto.UpdateResourcesRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ResourcesService {

    private final ResourcesRepository resourcesRepository;

    public ResourcesResponse createResources(CreateResourcesRequest createResourcesRequest) {
        Resources resources = resourcesRepository.save(createResourcesRequest.toResources());
        return ResourcesResponse.of(resources);
    }

    @Transactional(readOnly = true)
    public List<ResourcesResponse> findResourcess(Long lastResourcesId) {
        Pageable pageable = PageRequest.of(START_PAGE_NO, PAGE_SIZE, Sort.by("id").descending());

        if (lastResourcesId == null) {
            lastResourcesId = resourcesRepository.findFirstByOrderByIdDesc()
                .map(Resources::getId)
                .orElse(0L);
        }

        List<Resources> resourcess = resourcesRepository.findLatest(lastResourcesId, pageable);

        return resourcess.stream()
            .map(resources -> ResourcesResponse.of(resources))
            .collect(Collectors.toList());
    }


    public Resources findById(Long id) {
        return resourcesRepository.findById(id)
            .orElseThrow(RuntimeException::new);
    }

    public Resources replace(Long id, UpdateResourcesRequest request) {
        return resourcesRepository.findById(id)
            .map(resources -> {
                resources.update(request.toResources());
                return resources;
            })
            .orElseGet(() -> {
                Resources newResources = request.toResources(id);
                return resourcesRepository.save(newResources);
            });
    }

    public void deleteResources(Long id) {
        resourcesRepository.deleteById(id);
    }
}
