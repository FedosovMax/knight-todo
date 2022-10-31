package com.knighttodo.todocore.character.service.impl;

import com.knighttodo.todocore.character.domain.BonusVO;
import com.knighttodo.todocore.character.domain.SkillVO;
import com.knighttodo.todocore.character.exception.SkillNotFoundException;
import com.knighttodo.todocore.character.service.BonusService;
import com.knighttodo.todocore.character.service.SkillService;
import com.knighttodo.todocore.character.service.privatedb.mapper.SkillMapper;
import com.knighttodo.todocore.character.service.privatedb.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;
    private final BonusService bonusService;

    @Override
    public SkillVO save(SkillVO skillVO) {
        skillVO.setBonuses(fetchBonusesForSkill(skillVO));
        return skillMapper.toSkillVO(skillRepository.save(skillMapper.toSkill(skillVO)));
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
        return skillRepository.findAll().stream().map(skillMapper::toSkillVO).collect(Collectors.toList());
    }

    @Override
    public SkillVO findById(String skillId) {
        return skillRepository.findById(skillId).map(skillMapper::toSkillVO).orElseThrow(
            () -> new SkillNotFoundException(String.format("Skill with such id:%s can't be found", skillId)));
    }

    @Override
    public SkillVO updateSkill(String skillId, SkillVO changedSkillVO) {
        SkillVO skillVO = findById(skillId);

        skillVO.setName(changedSkillVO.getName());
        skillVO.setDescription(changedSkillVO.getDescription());
        skillVO.setBonuses(fetchBonusesForSkill(changedSkillVO));

        return skillMapper.toSkillVO(skillRepository.save(skillMapper.toSkill(skillVO)));
    }

    @Override
    public void deleteById(String skillId) {
        skillRepository.deleteById(skillId);
    }
}
