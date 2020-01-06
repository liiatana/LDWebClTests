package ru.lanit.ld.wc.model;


import io.qameta.allure.Step;

import java.util.*;

public class InstructionTypes {
    public List<InstructionType> typeList;

    public InstructionTypes(List<InstructionType> typeList) {
        this.typeList = typeList;
    }

    public InstructionTypes() {
    }

    // возвращает один любой тип уведомления
    @Step("Найти любой тип уведомления")
    public InstructionType getAnyNoticeType() {
        Collections.shuffle(this.typeList);
        return this.typeList.stream()
                .filter(x -> x.getUseControl() == false)
                .findAny().orElse(null);
    }


    // возвращает один любой тип задания
    public InstructionType getAnyTaskType() {
        Collections.shuffle(this.typeList);
        return this.typeList.stream()
                .filter(x -> x.getUseControl() == true)
                .findAny().orElse(null);
    }


    //возвращает тип по ID
    public InstructionType getInstructionTypeNameById(int instructionTypeId) {
        Collections.shuffle(this.typeList);
        return this.typeList.stream()
                .filter(x -> x.getId() == instructionTypeId)
                .findFirst().orElse(null);
    }


    // возвращает один любой тип задания с ДПО
    public InstructionType getControlTypeWithClerical() {
        Collections.shuffle(this.typeList);
        return this.typeList.stream()
                .filter(x -> x.getUseControl() == true && x.getOperationID() > 0)
                .findAny().orElse(null);

    }


    // возвращает один любой тип задания, у которого проводится проверка на заполненность текста отчета
    public InstructionType getControlTypeForPossitiveCheck(int checkType) {
        Collections.shuffle(this.typeList);
        return this.typeList.stream()
                .filter(x -> x.getUseControl() == true && x.getCheckReportTypePositive() == checkType)
                .findAny().orElse(null);
    }


    // возвращает один любой тип задания, который можно перенаправить только как Контрольный
    public InstructionType getAnyWithRedirectedAsControl() {
        Collections.shuffle(this.typeList);
        return this.typeList.stream()
                .filter(x -> x.isRedirectAsControl() == true)
                .findAny().orElse(null);

    }

    //возвращает коллекцию названий типов
    public Set<String> getInstructionTypesListAsString(boolean onlyControlTypes) {
        Set<String> collect1 = new HashSet<>();

        if (onlyControlTypes) {
            this.typeList.stream()
                    .filter(x -> x.getUseControl() == true)
                    .forEach(x -> collect1.add(x.getName().toUpperCase()));
        } else {
            this.typeList.stream()
                    .forEach(x -> collect1.add(x.getName().toUpperCase()));
        }
        return collect1;
    }


    //возвращает любой тип
    public InstructionType getAnyType() {
        Collections.shuffle(this.typeList);
        return this.typeList.stream()
                .findAny().orElse(null);
    }

    // возвращает один любой с пустым текстом по умолчанию
    public InstructionType getAnyWithDefaultSubjectEmpty() {
        Collections.shuffle(this.typeList);
        return this.typeList.stream()
                .filter(x -> x.getTemplateText() == null)
                .findAny().orElse(null);
    }

    // возвращает один любой с НЕпустым текстом по умолчанию
    public InstructionType getAnyWithDefaultSubjectNonEmpty() {
        Collections.shuffle(this.typeList);
        return this.typeList.stream()
                .filter(x -> x.getTemplateText() != null)
                .findAny().orElse(null);
    }

    public boolean findType(InstructionType instructionType) {
        /*long count = typeList.stream()
                .filter(x -> x == instructionType)
                .count();*/
        if(typeList.stream().filter(x -> x == instructionType).count()>0)return true;
        else return false;

    }

    // возвращает один любой тип задания кроме заданного
    public InstructionType getAnyTaskTypeExcept(InstructionType excludeType) {
        Collections.shuffle(this.typeList);
        return this.typeList.stream()
                .filter(x -> x.getUseControl() == true)
                .filter(x -> x.getId() != excludeType.getId())
                .findAny().orElse(null);
    }
}
