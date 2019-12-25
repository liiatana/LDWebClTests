package ru.lanit.ld.wc.model;


import io.qameta.allure.Step;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        return this.typeList.stream()
                .filter(x -> x.getUseControl() == false)
                .findAny().orElse(null);
    }


    // возвращает один любой тип задания
    public InstructionType getAnyTaskType() {
        return this.typeList.stream()
                .filter(x -> x.getUseControl() == true)
                .findAny().orElse(null);
    }


    //возвращает тип по ID
    public InstructionType getInstructionTypeNameById(int instructionTypeId) {
        return this.typeList.stream()
                .filter(x -> x.getId() == instructionTypeId)
                .findFirst().orElse(null);
    }


    // возвращает один любой тип задания с ДПО
    public InstructionType getControlTypeWithClerical() {
        return this.typeList.stream()
                .filter(x -> x.getUseControl() == true && x.getOperationID() > 0)
                .findAny().orElse(null);

    }


    // возвращает один любой тип задания, у которого проводится проверка на заполненность текста отчета
    public InstructionType getControlTypeForPossitiveCheck(int checkType) {

        return this.typeList.stream()
                .filter(x -> x.getUseControl() == true && x.getCheckReportTypePositive() == checkType)
                .findAny().orElse(null);
    }


    // возвращает один любой тип задания, который можно перенаправить только как Контрольный
    public InstructionType getAnyWithRedirectedAsControl() {
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
        return this.typeList.stream()
                .findAny().orElse(null);
    }

    // возвращает один любой с пустым текстом по умолчанию
    public InstructionType getAnyWithDefaultSubjectEmpty() {

        return this.typeList.stream()
                .filter(x -> x.getTemplateText() == null)
                .findAny().orElse(null);
    }

    // возвращает один любой с НЕпустым текстом по умолчанию
    public InstructionType getAnyWithDefaultSubjectNonEmpty() {

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
}
