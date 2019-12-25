package ru.lanit.ld.wc.model;

import java.util.ArrayList;
import java.util.List;

public class Reports {

    public List<Report> reports;

    @Override
    public String toString() {
        return "Reports{" +
                "reports=" + reports +
                '}';
    }

    public Reports(Instruction instruction) {


        reports = new ArrayList<Report>();

        Report report = new Report(instruction);
        report.withInitiatorID(instruction.getReceiverID()[0]);
        report.withReceiverId(instruction.getReportReceiverID());

        reports.add(0, report);

        if (instruction.getReceiverID().length > 1) {

            //Report report_next = new Report(report);
            //report_next.withInstructionId(instruction.getInstructionId() + 1);

            int reportReceiverId;

            if (instruction.isReportToExecutive() == true) {
                reportReceiverId = instruction.getReceiverID()[0];
            } else reportReceiverId = instruction.getReportReceiverID();

            //report_next.withReceiverId(reportReceiverId);

            int countReports;
            if (instruction.getSendType() == 1) {
                if (instruction.isWithExecutive() == true) {
                    countReports = 2;
                } else countReports = 1;
            } else countReports = instruction.getReceiverID().length;


            for (int i = 1; i < countReports; i++) {
                Report report1 = new Report(report);
                report1.withInitiatorID(instruction.getReceiverID()[i]);
                report1.withReceiverId(reportReceiverId);
                report1.withInstructionId(instruction.getInstructionId() + i);
                reports.add(i, report1);
            }
        }




    }
}
