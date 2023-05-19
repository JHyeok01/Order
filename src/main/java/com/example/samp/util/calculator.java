package com.example.samp.util;

import com.example.samp.dto.MatDTO;
import com.example.samp.dto.OrdersDTO;
import com.example.samp.dto.ProductDTO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class calculator {

    public static void main(String[] args) {

        LocalDateTime cabDeliveryDate;

        OrdersDTO order = new OrdersDTO();
        ProductDTO product = new ProductDTO();
        MatDTO matCab = new MatDTO();
        MatDTO matPau = new MatDTO();
        MatDTO matBox = new MatDTO();
        List<MatDTO> matDTOList = new ArrayList<>();


        // 원자재 재고
        matCab.setMatName("양배추");
        matCab.setMatNum(0);

        matPau.setMatName("파우치");
        matPau.setMatNum(123120);

        matBox.setMatName("박스");
        matBox.setMatNum(1230);

        matDTOList.add(matCab);
        matDTOList.add(matPau);
        matDTOList.add(matBox);

        // 수주
        order.setId(1L);
        order.setOrderBy("테스트 주문자");
        order.setProduct("양배추즙");
        order.setBox(8212);
        order.setStatus("승인");
        order.setOrderDate(LocalDateTime.now());

        // 완제품 재고
        product.setProduct("양배추");
        product.setNum(99);

        System.out.println("==========================");
        System.out.println(matCab.getMatNum() * 2 * 0.8 / 0.08 / 30);
        System.out.println("==========================");


//        LocalDateTime orderTime = LocalDateTime.now(); // 수주 승인 시간
//
//        orderTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)); // yyyy-MM-dd 요일

        // product 확인 --> 만약 product의 수량이 수주건 보다 작으면 --> mat 확인 --> mat 자재량도 작으면 --> 자재 발주 진행

        // 양배추 생산 가능 수량
        double cabProductionCapacity = matDTOList.get(0).getMatNum() * 2 * 0.8 / 0.08 / 30;

        // 수주량 > 완재품 재고
        if(order.getBox() > product.getNum()){
            // 원자재 재고 확인
            System.out.println("원자재 재고 확인");

            if(order.getBox() <= cabProductionCapacity) { // 양배추 생산 가능 여부
                System.out.println("양배추 충분");
            }else {
                System.out.println("양배추 부족");
                // 양배추 발주 수량
                double orderCabQuantity = order.getBox() * 1.5;
                System.out.println("양배추 발주 수량");

                // 양배추 최소 발주량이 1ton임
                if (orderCabQuantity < 1000){
                    // 발주량이 1000보다 작을 때
                    orderCabQuantity = 1000;
                }else{
                    // 발주량이 1000보다 클 때
                    orderCabQuantity = Math.ceil(orderCabQuantity / 1000);
                    System.out.println("실제 발주량");
                    System.out.println(orderCabQuantity); // 단위 ton
                }


                // 입고는 월, 수, 금 오전 10:00 창고에 도착
                LocalDateTime cabOrderTime = LocalDateTime.now(); // 양배추를 주문 한 시간
                if (cabOrderTime.getHour() < 12){
                    // 12시 이전 주문건
                    // 1=월 ... 7=일
                    System.out.println(cabOrderTime.plusDays(2).getDayOfWeek().getValue());

                    if (cabOrderTime.getDayOfWeek().getValue() == 1 || cabOrderTime.getDayOfWeek().getValue() == 3){
                        // 월요일 수요일
                        cabDeliveryDate = cabOrderTime.toLocalDate().plusDays(2).atTime(10,0);
                        System.out.println("12시 이전 주문건 월, 수요일");
                        System.out.println(cabDeliveryDate);
                    }else if(cabOrderTime.getDayOfWeek().getValue() == 2 || cabOrderTime.getDayOfWeek().getValue() == 7){
                        // 화요일 일요일
                        cabDeliveryDate = cabOrderTime.toLocalDate().plusDays(3).atTime(10,0);
                        System.out.println("12시 이전 주문건 화, 일요일");
                        System.out.println(cabDeliveryDate);
                    }else  if(cabOrderTime.getDayOfWeek().getValue() == 4 || cabOrderTime.getDayOfWeek().getValue() == 6){
                        // 목요일 토요일
                        cabDeliveryDate = cabOrderTime.toLocalDate().plusDays(4).atTime(10,0);
                        System.out.println("12시 이전 주문건 목, 토요일");
                        System.out.println(cabDeliveryDate);
                    }else {
                        // 금요일
                        cabDeliveryDate = cabOrderTime.toLocalDate().plusDays(5).atTime(10,0);
                        System.out.println("12시 이전 주문건 금요일");
                        System.out.println(cabDeliveryDate);
                    }

                }else{
                    // 12시 이후 주문건
                    if (cabOrderTime.getDayOfWeek().getValue() == 2 || cabOrderTime.getDayOfWeek().getValue() == 7){
                        // 화요일 일요일
                        cabDeliveryDate = cabOrderTime.toLocalDate().plusDays(3).atTime(10,0);
                        System.out.println("12시 이후 주문건 화, 일요일");
                        System.out.println(cabDeliveryDate);

                    }else if(cabOrderTime.getDayOfWeek().getValue() == 1 || cabOrderTime.getDayOfWeek().getValue() == 6){
                        // 월요일 토요일
                        cabDeliveryDate = cabOrderTime.toLocalDate().plusDays(4).atTime(10,0);
                        System.out.println("12시 이후 주문건 월, 목요일");
                        System.out.println(cabDeliveryDate);

                    }else  if(cabOrderTime.getDayOfWeek().getValue() == 3 || cabOrderTime.getDayOfWeek().getValue() == 5){
                        // 수요일 금요일
                        cabDeliveryDate = cabOrderTime.toLocalDate().plusDays(5).atTime(10,0);
                        System.out.println("12시 이후 주문건 수, 금요일");
                        System.out.println(cabDeliveryDate);
                    }else {
                        // 목요일
                        cabDeliveryDate = cabOrderTime.toLocalDate().plusDays(6).atTime(10,0);
                        System.out.println("12시 이후 주문건 목요일");
                        System.out.println(cabDeliveryDate);
                    }

                }

                // 이전 발주 건 조회하고 자재발주 수량이 오버 되는지 체크 해야됨

            }

            if (matDTOList.get(1).getMatNum() >= cabProductionCapacity * 30) { // 재고 창고 파우치 >= 양배추 생산량의 파우치 필요
                System.out.println("파우치 충분");
            }else {
                System.out.println("파우치 부족");
                // 파우치 발주 구현
                // 이전 발주 건 조회하고 자재발주 수량이 오버 되는지 체크 해야됨
            }

            if (matDTOList.get(2).getMatNum() >= order.getBox()){ // 창고 박스 > 수주 박스
                System.out.println("박스 충분");
            }else {
                System.out.println("박스 부족");
                // 박스 발주 구현
                // 이전 발주 건 조회하고 자재발주 수량이 오버 되는지 체크 해야됨
            }
        }else{ // 완제품이 충분할경우
            System.out.println("출하");
            // 출하 구현
        }


        // 원료계량 작업 여부 확인
        boolean materialProcessYN = true; // true = 작업중, 리드타임 false = 대기중

        Queue<Integer> materialProcessQueue = new LinkedList<>(); // 원료계량 작업 큐

        if (materialProcessYN) { // 수주가 들어가기 전 작업 가능한지 확인
            materialProcessQueue.add(order.getId().intValue()); // 공정 작업이 true로 작업 중 or 리드타임이라 큐에 1번 수주건을 추가
        } else {
            // 작업 진행
        }


    }

}
