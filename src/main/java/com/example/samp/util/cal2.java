package com.example.samp.util;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class cal2 {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    //작업 시작시간에 제약사항 주는 메소드
    public static LocalDateTime workTimeStart(LocalDateTime startTime) {
        LocalTime morningStart = LocalTime.of(9, 0);
        LocalTime morningEnd = LocalTime.of(12, 0);

        LocalTime lunchStart = LocalTime.of(12, 0);
        LocalTime lunchEnd = LocalTime.of(13, 0);

        LocalTime afternoonStart = LocalTime.of(13, 0);
        LocalTime afternoonEnd = LocalTime.of(18, 0);


        if (startTime.getDayOfWeek() == DayOfWeek.FRIDAY && startTime.toLocalTime().isAfter(LocalTime.of(18, 0))) {
            startTime = startTime.plusDays(3).with(morningStart); // 금요일이고 시작시간이 18시 이후일 때, 월요일 9시에 작업 시작
        } else {
            if (startTime.getDayOfWeek() == DayOfWeek.SATURDAY) {
                startTime = startTime.plusDays(2).with(morningStart); // 토요일이면 +이틀 후 9시에 작업 시작
            } else if (startTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
                startTime = startTime.plusDays(1).with(morningStart); // 일요일이면 +하루 후 9시에 작업 시작
            } else {
                if (startTime.toLocalTime().isBefore(morningStart)) {
                    startTime = startTime.with(morningStart); // 9시 이전에 시작하면, 오전 9시부터 작업 시작
                } else if (startTime.toLocalTime().isAfter(afternoonEnd)) {
                    startTime = startTime.plusDays(1).with(morningStart); // 18시 이후에 시작하면, 오전 9시부터 작업 시작
                } else if (startTime.toLocalTime().isAfter(lunchStart) && startTime.toLocalTime().isBefore(afternoonStart)) {
                    startTime = startTime.with(afternoonStart); // 점심시간에 이후 시작하고 오후작업시간 이전에 시작하면(둘 다 같은 말), 13시부터 작업 시작
                } else if (startTime.toLocalTime().isAfter(afternoonEnd)) { //18시 이후에 끝나면,
                    if (startTime.getDayOfWeek() == DayOfWeek.FRIDAY) {
                        startTime = startTime.plusDays(3).with(morningStart); // 금요일이라면 +사흘 후 9시에 작업 시작
                    } else {
                        startTime = startTime.plusDays(1).with(morningStart); // 다음 날 오전 9시부터 작업 시작
                    }
                }
            }
        }

        return startTime;
    }


    //양배추 계산
    public static LocalDateTime calCab(int box) {

        int totalpau = 30 * box;
        double remainCab =0;
        boolean isStick =false;

        //필요한 즙 무게
        double cabageJu = Math.ceil(totalpau* 80);

        //수율을 반영하기위해 투입되는 양배추와 물을 합친 무게
        double requiredCabWat = Math.ceil(totalpau *80 /8 *10);

        //실제 필요한 양배추 양
        double requiredCab = requiredCabWat /2;

        //양배추의 최소 주문수량 = 1,000,000g = 1ton
        double offerCab = Math.ceil(requiredCab /1000000) *1000000;

        // 잔여 양배추 양
        // 재고로 저장
        remainCab = offerCab - requiredCab + remainCab;

        System.out.println();
        System.out.println("주문 박스 수량: " + box+ " box");
        System.out.println("필요한 양배추 파우치 총량: " + totalpau+" ea");
        System.out.println("양배추 추출액 필요량: " + cabageJu + " ml");
        System.out.println("80%의 수율을 반영한 필요투입량: " + requiredCabWat/1000 + " kg");
        System.out.println("필요한 양배추양: " + requiredCab/1000 + " kg");
        System.out.println("발주해야하는 양배추양: " + offerCab/1000 + " kg");
        System.out.println("잔여 양배추양 : " + remainCab/1000 + " kg");
        System.out.println();

        LocalDateTime weightEnd =calWeigh();
        LocalDateTime washEnd =calWash(weightEnd, requiredCab);
        LocalDateTime extractEnd =calExtract(washEnd, requiredCab);
        LocalDateTime blendEnd =calBlend(extractEnd, isStick);
        LocalDateTime packingEnd =calPacking(blendEnd, totalpau, isStick);
        LocalDateTime testEnd =calTesting(packingEnd, totalpau);
        LocalDateTime coolingEnd =calCooling(testEnd);
        LocalDateTime boxingEnd =calBoxing(coolingEnd, box);

        return workTimeStart(boxingEnd);

    }


    //흑마늘 계산
    public static LocalDateTime calGal(int box) {

        int totalpau = 30 * box;
        double remainGal =0;
        boolean isStick =false;


        //필요한 흑마늘 추출액 무게
        double galicJu = Math.ceil(totalpau* 20);

        //수율을 반영하기 위해 추출기에 투입되는 흑마늘과 물을 합친 무게
        double requiredGalWat = Math.ceil(totalpau *20 /6 *10);

        //실제 필요한 흑마늘의 양
        double requiredGal = requiredGalWat/4;

        //흑마늘의 최소 주문수량 = 10,000g
        double offerGal = Math.ceil(requiredGal /10000) *10000;

        //잔여 흑마늘 양
        //재고로 저장해야함
        remainGal = offerGal - requiredGal + remainGal;

        System.out.println();
        System.out.println("주문 박스 수량: " + box+ " box");
        System.out.println("필요한 흑마늘 파우치 총량: " + totalpau+" ea");
        System.out.println("흑마늘 추출액 필요량: " + galicJu + " ml");
        System.out.println("60%의 수율을 반영한 필요투입량: " + requiredGalWat/1000 + " kg");
        System.out.println("필요한 흑마늘양: " + requiredGal/1000 + " kg");
        System.out.println("발주해야하는 흑마늘양: " + offerGal/1000 + " kg");
        System.out.println("잔여 흑마늘양 : " + remainGal/1000 + " kg");
        System.out.println();

        LocalDateTime weightEnd =calWeigh();
        LocalDateTime washEnd =calWash(weightEnd, requiredGal);
        LocalDateTime extractEnd =calExtract(washEnd, requiredGal);
        LocalDateTime blendEnd =calBlend(extractEnd, isStick);
        LocalDateTime packingEnd =calPacking(blendEnd, totalpau, isStick);
        LocalDateTime testEnd =calTesting(packingEnd, totalpau);
        LocalDateTime coolingEnd =calCooling(testEnd);
        LocalDateTime boxingEnd =calBoxing(coolingEnd, box);

        return workTimeStart(boxingEnd);


    }


    //석류 계산
    public static LocalDateTime calPom(int box) {

        int totalpau = 25 * box;
        double remainPom =0;
        double remainCol =0;
        boolean isStick =true;


        //필요한 석류 추출액 무게
        double pomJu = Math.ceil(totalpau* 5);

        //필요한 콜라겐 무게
        double colJu = Math.ceil(totalpau* 2);

        //혼합기에 들어가는 석류농축액과 콜라겐과 물을 합친 무게
        double requiredPomColWat = Math.ceil(pomJu + colJu + totalpau* 8);

        //석류농축액의 최소 주문수량 = 5,000g
        double offerPom = Math.ceil(pomJu /5000) * 5000;

        //콜라겐의 최소 주문수량 = 5,000g
        double offerCol =Math.ceil(colJu /5000) * 5000;

        //잔여 석류농축액 양
        //재고로 저장
        remainPom =offerPom - pomJu + remainPom;

        //잔여 콜라겐 양
        //재고로 저장
        remainCol =offerCol - colJu + remainCol;

        System.out.println();
        System.out.println("주문 박스 수량: " + box+ " box");
        System.out.println("필요한 석류 파우치 총량: " + totalpau+" ea");
        System.out.println("석류 농축액 필요량: " + pomJu + " ml");
        System.out.println("콜라겐 필요량: " + colJu + " ml");
        System.out.println("석류농축액 + 콜라겐 + 정제수 투입량: " + requiredPomColWat + " g");
        System.out.println("발주해야하는 석류 농축액양: " + offerPom /1000 + " kg");
        System.out.println("발주해야하는 콜라겐양: " + offerCol /1000 + " kg");
        System.out.println("잔여 석류농축액양 : " + remainPom + " g");
        System.out.println("잔여 콜라겐양 : " + remainCol + " g");
        System.out.println();

        LocalDateTime extractEnd =calWeigh();
        LocalDateTime blendEnd =calBlend(extractEnd, isStick);
        LocalDateTime packingEnd =calPacking(blendEnd, totalpau, isStick);
        LocalDateTime testEnd =calTesting(packingEnd, totalpau);
        LocalDateTime coolingEnd =calCooling(testEnd);
        LocalDateTime boxingEnd =calBoxing(coolingEnd, box);

        return workTimeStart(boxingEnd);

    }


    //매실 계산
    public static LocalDateTime calPlu(int box) {

        int totalpau = 25 * box;
        double remainPlu =0;
        double remainCol =0;
        boolean isStick =true;


        //필요한 매실 추출액 무게
        double pluJu = Math.ceil(totalpau* 5);

        //필요한 콜라겐 무게
        double colJu = Math.ceil(totalpau* 2);

        //혼합기에 들어가는 매실농축액과 콜라겐과 물을 합친 무게
        double requiredPluColWat = Math.ceil(pluJu + colJu + totalpau* 8);

        //매실농축액의 최소 주문수량 = 5,000g
        double offerPlu = Math.ceil(pluJu /5000) * 5000;

        //콜라겐의 최소 주문수량 = 5,000g
        double offerCol =Math.ceil(colJu /5000) * 5000;

        //잔여 매실농축액 양
        //재고로 저장
        remainPlu =offerPlu - pluJu + remainPlu;

        //잔여 콜라겐 양
        //재고로 저장
        remainCol =offerCol - colJu + remainCol;

        System.out.println();
        System.out.println("주문 박스 수량: " + box+ " box");
        System.out.println("필요한 매실 파우치 총량: " + totalpau+" ea");
        System.out.println("매실 농축액 필요량: " + pluJu + " ml");
        System.out.println("콜라겐 필요량: " + colJu + " ml");
        System.out.println("매실농축액 + 콜라겐 + 정제수 투입량: " + requiredPluColWat + " g");
        System.out.println("발주해야하는 매실 농축액양: " + offerPlu /1000 + " kg");
        System.out.println("발주해야하는 콜라겐양: " + offerCol /1000 + " kg");
        System.out.println("잔여 매실농축액양 : " + remainPlu + " g");
        System.out.println("잔여 콜라겐양 : " + remainCol + " g");
        System.out.println();

        LocalDateTime extractEnd =calWeigh();
        LocalDateTime blendEnd = calBlend(extractEnd, isStick);
        LocalDateTime packingEnd = calPacking(blendEnd, totalpau, isStick);
        LocalDateTime testEnd = calTesting(packingEnd, totalpau);
        LocalDateTime coolingEnd = calCooling(testEnd);
        LocalDateTime boxingEnd = calBoxing(coolingEnd, box);

        return workTimeStart(boxingEnd);


    }


    //원료계량 weight
    public static LocalDateTime calWeigh() {

        LocalDateTime ibTime =LocalDateTime.now();

        // 원료계량 시작 = weightStart = 자재투입시작시간
        LocalDateTime weightStart = ibTime;
        weightStart =workTimeStart(weightStart);
        System.out.println("원료계량 시작: " + weightStart.format(formatter));


        LocalDateTime weightEnd = weightStart.plusMinutes(50);
        System.out.println("원료계량 끝 : " + weightEnd.format(formatter));
        System.out.println();

        return weightEnd;

    }


    //전처리 wash
    public static LocalDateTime calWash(LocalDateTime weightEnd, double requiredMat) {

        LocalDateTime washStart = weightEnd;
        washStart =workTimeStart(washStart);
        System.out.println("전처리 시작: " + washStart.format(formatter));

        LocalDateTime washEnd = washStart.plusMinutes(20).plusMinutes((long) Math.ceil(requiredMat/1000 * 60 / 1000));
        System.out.println("전처리 끝: " + washEnd.format(formatter));
        System.out.println();


        return washEnd;
    }


    //추출 extract
    public static LocalDateTime calExtract(LocalDateTime washEnd, double requiredMat) {

        LocalDateTime extractStart = washEnd;
        extractStart =workTimeStart(extractStart);
        System.out.println("추출 시작: " + extractStart.format(formatter));

        LocalDateTime extractEnd = extractStart.plusMinutes(60).plusHours((long) Math.ceil(((requiredMat/1000 * 24)/1000)*2));
        System.out.println("추출 끝: " + extractEnd.format(formatter));
        System.out.println();

        return extractEnd;
    }


    // 혼합 및 살균 blend
    public static LocalDateTime calBlend(LocalDateTime extractEnd, boolean isStick) {


        LocalDateTime blendEnd = null;

        if (!isStick) { //즙일 때
            LocalDateTime blendStart = extractEnd;
            System.out.println("혼합 및 살균 시작 : " + blendStart.format(formatter));

            blendEnd = blendStart.plusHours(24);
            System.out.println("혼합 및 살균 끝 : " + blendEnd.format(formatter));
            System.out.println();

        } else {
            LocalDateTime blendStart = extractEnd;
            blendStart =workTimeStart(blendStart);
            System.out.println("혼합 및 살균 시작 : " + blendStart.format(formatter));

            blendEnd = blendStart.plusMinutes(20).plusHours(8);
            System.out.println("혼합 및 살균 끝 : " + blendEnd.format(formatter));
            System.out.println();

        }


        return blendEnd;
    }


    //충진 packing
    public static LocalDateTime calPacking(LocalDateTime blendEnd, int totalpau, boolean isStick) {


        LocalDateTime packingStart = blendEnd;
        packingStart =workTimeStart(packingStart);
        System.out.println("충진 시작: " + packingStart.format(formatter));

        LocalDateTime packingEnd =null;

        if (!isStick) {
            packingEnd = packingStart.plusMinutes(20).plusHours((long) Math.ceil(Math.ceil(totalpau * 60) / 3500 / 60));
        } else {
            packingEnd = packingStart.plusMinutes(20).plusHours((long) Math.ceil(Math.ceil(totalpau * 60) / 3000 / 60));
        }

        System.out.println("충진 끝: " + packingEnd.format(formatter));
        System.out.println();


        return packingEnd;
    }


    // 검사 testing
    public static LocalDateTime calTesting(LocalDateTime packingEnd, int totalpau) {


        LocalDateTime testStart = packingEnd;
        testStart =workTimeStart(testStart);
        System.out.println("검사 시작: " + testStart.format(formatter));

        LocalDateTime testEnd =testStart.plusMinutes(10).plusHours((long) Math.ceil(Math.ceil(totalpau * 60) / 5000) / 60);
        System.out.println("검사 끝: " + testEnd.format(formatter));
        System.out.println();


        return testEnd;
    }


    // 냉각 cooling
    public static LocalDateTime calCooling(LocalDateTime testEnd) {


        LocalDateTime coolingStart = testEnd;
        //제약사항 없음
        System.out.println("냉각 시작: " + coolingStart.format(formatter));

        LocalDateTime coolingEnd = coolingStart.plusDays(1).with(LocalTime.of(9, 0));
        System.out.println("냉각 끝: " + coolingEnd.format(formatter));
        System.out.println();

        return coolingEnd;
    }


    // 포장 boxing
    public static LocalDateTime calBoxing(LocalDateTime coolingEnd, int box) {


        LocalDateTime boxingStart = coolingEnd;
        boxingStart =workTimeStart(boxingStart);
        System.out.println("포장 시작: " + boxingStart.format(formatter));

        int workCapacity = 20; // 한 명의 인부가 1시간 동안 작업할 수 있는 box 수
        int workTimePerSec = 18; // 작업 시간 (초)
        double requiredTime = (double)Math.ceil((box*workTimePerSec)/60); // 소요 시간 계산
        LocalTime lunchStart = LocalTime.of(12, 0);
        LocalTime lunchEnd = LocalTime.of(13, 0);

        LocalDateTime boxingEnd = boxingStart.plusMinutes(20);
        boxingEnd =boxingEnd.plusMinutes((long) requiredTime);

        boxingEnd = boxingTimeSet(boxingEnd);


        LocalDateTime passdays = boxingStart;

        while (passdays.toLocalDate().isBefore(boxingEnd.toLocalDate())) {

            passdays = passdays.plusDays(1);
            if(passdays.getDayOfWeek()==DayOfWeek.SATURDAY || passdays.getDayOfWeek()==DayOfWeek.SUNDAY ) {

            }else {
                boxingEnd = boxingEnd.plusHours(1);
            }
        }


        boxingEnd = boxingTimeSet(boxingEnd);
        boxingEnd = boxingTimeSet(boxingEnd);
        if(!boxingEnd.toLocalTime().isBefore(lunchStart)) {
            boxingEnd =boxingEnd.plusHours(1);
        }
        System.out.println("포장 끝: " + boxingEnd.format(formatter));
        System.out.println();


        return boxingEnd;
    }

    //수작업 시간 제한
    public static LocalDateTime boxingTimeSet(LocalDateTime boxingEnd) {

        LocalTime morningStart = LocalTime.of(9, 0);

        LocalTime lunchStart = LocalTime.of(12, 0);

        LocalTime afternoonStart = LocalTime.of(13, 0);
        LocalTime afternoonEnd = LocalTime.of(18, 0);

        if (boxingEnd.getDayOfWeek() == DayOfWeek.FRIDAY && boxingEnd.toLocalTime().isAfter(afternoonEnd)) {
            boxingEnd = boxingEnd.plusDays(2).plusHours(15); // 월요일 아침 9시

        } else if (boxingEnd.getDayOfWeek() == DayOfWeek.SATURDAY) {
            boxingEnd = boxingEnd.plusDays(2).plusHours(15);

        } else if (boxingEnd.getDayOfWeek() == DayOfWeek.SUNDAY) {
            boxingEnd = boxingEnd.plusDays(2).plusHours(15);

        } else if (boxingEnd.toLocalTime().isAfter(afternoonEnd)) {
            boxingEnd = boxingEnd.plusHours(15); // 오전 9시부터 작업 시작

        } else if (boxingEnd.toLocalTime().isBefore(morningStart)) {
            boxingEnd = boxingEnd.plusHours(15); // 오전 9시부터 작업 시작

        } else if (boxingEnd.toLocalTime().isAfter(lunchStart) && boxingEnd.toLocalTime().isBefore(afternoonStart)) {
            boxingEnd = boxingEnd.plusHours(1); // 오후 1시부터 작업 시작

        } else if (boxingEnd.toLocalTime().isAfter(afternoonEnd)) {
            boxingEnd = boxingEnd.plusDays(1).with(morningStart); // 다음 날 오전 9시부터 작업 시작
        }

        return boxingEnd;
    }



    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LocalDateTime deliverDateTime =LocalDateTime.now();

        System.out.print("재료를 선택해 주세요(1 : 양배추, 2 : 흑마늘, 3 : 석류, 4 : 매실) : ");

        int ch = sc.nextInt();
        System.out.println();
        System.out.print("입력할 box 수량: ");
        int box = sc.nextInt();

        if (ch ==1)  deliverDateTime =calCab(box);
        else if (ch ==2) deliverDateTime =calGal(box);
        else if (ch ==3) deliverDateTime =calPom(box);
        else if (ch ==4) deliverDateTime =calPlu(box);

        System.out.println("예상납품일자: " + deliverDateTime);
    }

}