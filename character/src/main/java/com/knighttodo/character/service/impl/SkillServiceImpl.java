package com.knighttodo.character.service.impl;

import com.knighttodo.character.domain.BonusVO;
import com.knighttodo.character.domain.SkillVO;
import com.knighttodo.character.exception.SkillNotFoundException;
import com.knighttodo.character.gateway.SkillGateway;
import com.knighttodo.character.service.BonusService;
import com.knighttodo.character.service.SkillService;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SkillServiceImpl implements SkillService {

    private final SkillGateway skillGateway;
    private final BonusService bonusService;

    @Override
    public SkillVO save(SkillVO skillVO) {
        skillVO.setBonuses(fetchBonusesForSkill(skillVO));
        return skillGateway.save(skillVO);
    }

    private List<BonusVO> fetchBonusesForSkill(SkillVO skillVO) {
        return skillVO.getBonuses()
            .stream()
            .map(BonusVO::getId)
            .map(bonusService::findById)
            .collect(Collectors.toList());
    }

    @Override
    public List<SkillVO> findAll() {
        return skillGateway.findAll();
    }

    @Override
    public SkillVO findById(String skillId) {
        return skillGateway.findById(skillId).orElseThrow(
            () -> new SkillNotFoundException(String.format("Skill with such id:%s can't be found", skillId)));
    }

    @Override
    public SkillVO updateSkill(String skillId, SkillVO changedSkillVO) {
        SkillVO skillVO = findById(skillId);

        skillVO.setName(changedSkillVO.getName());
        skillVO.setDescription(changedSkillVO.getDescription());
        skillVO.setBonuses(fetchBonusesForSkill(changedSkillVO));

        return skillGateway.save(skillVO);
    }

    @Override
    public void deleteById(String skillId) {
        skillGateway.deleteById(skillId);
    }
}
