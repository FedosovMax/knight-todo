package com.knighttodo.todoapp.factories;

import com.knighttodo.todoapp.gateway.character.response.ExperienceResponse;
import com.knighttodo.todoapp.gateway.privatedb.representation.RoutineInstance;
import com.knighttodo.todoapp.gateway.privatedb.representation.RoutineTodo;
import com.knighttodo.todoapp.gateway.privatedb.representation.RoutineTodoInstance;
import com.knighttodo.todoapp.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.todoapp.gateway.privatedb.representation.enums.Scariness;

import java.util.UUID;

public class RoutineTodoInstanceFactory {

    public static final String ROUTINE_TODO_INSTANCE_NAME = "Routine todo instance name";
    public static final Scariness SCARINESS_TODO = Scariness.NOT_SCARY;
    public static final Hardness HARDNESS_TODO = Hardness.EXTRAORDINARY;
    public static final boolean FALSE_TODO_READY = false;
    public static final boolean TRUE_TODO_READY = true;
    public static final int HARD_SCARY_EXPERIENCE = 24;

    private RoutineTodoInstanceFactory() {
    }

    public static RoutineTodoInstance routineTodoInstanceWithRoutineInstance(RoutineInstance routineInstance) {
        return RoutineTodoInstance
                .builder()
                .routineTodoInstanceName(ROUTINE_TODO_INSTANCE_NAME)
                .scariness(SCARINESS_TODO)
                .hardness(HARDNESS_TODO)
                .routineInstance(routineInstance)
                .ready(FALSE_TODO_READY)
                .build();
    }

    public static RoutineTodoInstance routineTodoInstanceWithRoutineAndRoutineTodoInstance(RoutineInstance routineInstance, RoutineTodo routineTodo) {
        return RoutineTodoInstance
                .builder()
                .routineTodoInstanceName(ROUTINE_TODO_INSTANCE_NAME)
                .scariness(SCARINESS_TODO)
                .hardness(HARDNESS_TODO)
                .routineInstance(routineInstance)
                .routineTodo(routineTodo)
                .ready(FALSE_TODO_READY)
                .build();
    }

    public static RoutineTodoInstance routineTodoInstanceWithRoutineInstanceAndRoutineTodo(RoutineInstance routineInstance,
                                                                                           RoutineTodo routineTodo) {
        return RoutineTodoInstance
                .builder()
                .routineTodoInstanceName(ROUTINE_TODO_INSTANCE_NAME)
                .scariness(SCARINESS_TODO)
                .hardness(HARDNESS_TODO)
                .routineInstance(routineInstance)
                .routineTodo(routineTodo)
                .ready(FALSE_TODO_READY)
                .build();
    }

    public static RoutineTodoInstance routineTodoInstanceWithRoutineReadyInstance(RoutineInstance routineInstance,
                                                                                  RoutineTodo routineTodo) {
        return RoutineTodoInstance
                .builder()
                .routineTodoInstanceName(ROUTINE_TODO_INSTANCE_NAME)
                .scariness(SCARINESS_TODO)
                .hardness(HARDNESS_TODO)
                .routineInstance(routineInstance)
                .routineTodo(routineTodo)
                .ready(TRUE_TODO_READY)
                .build();
    }

    public static ExperienceResponse experienceResponseInstance(UUID todoInstanceId) {
        return ExperienceResponse.builder().todoId(todoInstanceId).experience(HARD_SCARY_EXPERIENCE).build();
    }
}
