package com.knighttodo.character.service;

import com.knighttodo.character.domain.SkillVO;

import java.util.List;

public interface SkillService {

    SkillVO save(SkillVO skillVO);

    List<SkillVO> findAll();

    SkillVO findById(String skillId);

    SkillVO updateSkill(String skillId, SkillVO changedSkillVO);

    void deleteById(String skillId);
}
