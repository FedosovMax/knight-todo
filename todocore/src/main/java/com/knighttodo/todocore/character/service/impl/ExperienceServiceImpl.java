package com.knighttodo.todocore.character.service.impl;

import com.knighttodo.todocore.character.domain.ExperienceVO;
import com.knighttodo.todocore.character.service.CharacterService;
import com.knighttodo.todocore.character.service.ExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExperienceServiceImpl implements ExperienceService {

    private final CharacterService characterService;

    @Override
    public ExperienceVO calculateExperience(ExperienceVO experienceVO) {
        int scarynessExp = getScarinessExperience(experienceVO);
        int hardnessExp = getHardnessExperience(experienceVO);
        int experience = scarynessExp + hardnessExp;

        characterService.addExperience(experience, experienceVO.getUserId());
        experienceVO.setExperience(experience);
        return experienceVO;
    }

    private int getScarinessExperience(ExperienceVO experienceVO) {
        switch (experienceVO.getScariness()) {
            case "NOT_SCARY":
                return 1;
            case "SCARY":
                return 10;
            case "VERY_SCARY":
                return 50;
            default:
                return 0;
        }
    }

    private int getHardnessExperience(ExperienceVO experienceVO) {
        switch (experienceVO.getHardness()) {
            case "NOT_HARD":
                return 1;
            case "HARD":
                return 3;
            case "VERY_HARD":
                return 10;
            case "EXTRAORDINARY":
                return 30;
            case "IMPOSSIBLE":
                return 100;
            default:
                return 0;
        }
    }
}
