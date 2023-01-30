////package com.fengjr.tools;
////
////import com.fengjr.enums.LoanStatusEnum;
////import com.fengjr.model.CrowdFundingProject;
////import com.fengjr.model.Loan;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import java.util.regex.Pattern;
//
///**
// * Created by Alan on 2015/5/13.
// */
//@Component
//@Scope("singleton")
//public class ScoreTool {
//    private final double BASE = 10000000000000.000;
//    private final double REVERSE_BASE = 10000000000000000000000000.00;
//    private final double STATUS_OPENED = 1.0;
//    private final double STATUS_SCHEDULED = 2.0;
//    private final double STATUS_FINISHED = 3.0;
//    private final double STATUS_SETTLED = 4.0;
//    private final double STATUS_CLEARED = 5.0;
//
//    public Double getLoanScore(Loan loan) {
//        double score = BASE;
//
//        switch (LoanStatusEnum.valueOf(loan.getStatus())) {
//            case OPENED:
//                score += loan.getTimeOpen().getTime();
//                break;
//            case SCHEDULED:
//                score = BASE * STATUS_SCHEDULED;
//                score += 1.0 / loan.getTimeOpen().getTime() * REVERSE_BASE; // 即将开标为倒序
//                break;
//            case FINISHED:
//                score = BASE * STATUS_FINISHED;
//                score += loan.getTimeFinished().getTime();
//                break;
//            case SETTLED:
//                score = BASE * STATUS_SETTLED;
//                score += loan.getTimeSettled().getTime();
//                break;
//            case CLEARED:
//                score = BASE * STATUS_CLEARED;
//                score += loan.getTimeCleared().getTime();
//                break;
//            default:
//                break;
//        }
//        if (!StringUtils.isEmpty(loan.getTitle()) && Pattern.matches("(.*)(\\d{3})", loan.getTitle())) {
//            score += Double.valueOf("0." + loan.getTitle().substring(loan.getTitle().length() - 3, loan.getTitle().length()));
//        } else {
//        }
//        return score;
//    }
//
//    public Double getProjectScore(CrowdFundingProject crowdFundingProject) {
//        double score = BASE;
//
//        switch (LoanStatusEnum.valueOf(crowdFundingProject.getStatus())) {
//            case OPENED:
//                score += 1.0 / crowdFundingProject.getOpenTime().getTime() * REVERSE_BASE;
//                break;
//            case FINISHED:
//                score = BASE * STATUS_FINISHED;
//                score += 1.0 / crowdFundingProject.getOpenTime().getTime() * REVERSE_BASE;
//                break;
//            case SETTLED:
//                score = BASE * STATUS_SETTLED;
//                score += 1.0 / crowdFundingProject.getOpenTime().getTime() * REVERSE_BASE;
//                break;
//            default:
//                break;
//        }
//
//        score += crowdFundingProject.getOpenTime().getTime();
//
//        return score;
//    }
//}
