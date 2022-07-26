package com.knighttodo.character.gateway;

import com.knighttodo.character.domain.SkillVO;

import java.util.List;
import java.util.Optional;

public interface SkillGateway {

    SkillVO save(SkillVO skillVO);

    List<SkillVO> findAll();

    Optional<SkillVO> findById(String skillId);

    void deleteById(String skillId);
}
