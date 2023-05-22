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
        MatDTO mat = new MatDTO();
        MatDTO matPau = new MatDTO();
        MatDTO matBox = new MatDTO();
        MatDTO matCol = new MatDTO();
        List<MatDTO> matDTOList = new ArrayList<>();


        // 수주
        order.setId(1L);
        order.setOrderBy("테스트 주문자");
        order.setProduct("양배추즙");
        order.setBox(1000);
        order.setStatus("승인");
        order.setOrderDate(LocalDateTime.now());

        // 원자재 재고
        if (order.getProduct().length() < 5) {
            mat.setMatName(order.getProduct().substring(0, 3));
        } else {
            mat.setMatName(order.getProduct().substring(0, 2));
        }
        mat.setMatNum(1);

        matPau.setMatName("파우치");
        matPau.setMatNum(123120);

        matBox.setMatName("박스");
        matBox.setMatNum(1230);

        matCol.setMatName("콜라겐");
        matCol.setMatNum(120);

        matDTOList.add(mat); // 원자재
        matDTOList.add(matPau); // 파우치
        matDTOList.add(matBox); // 박스
        matDTOList.add(matCol); // 콜라겐


        // 완제품 재고
        product.setProduct(order.getProduct());
        product.setNum(99);


        // 수주량 > 완재품 재고
        if (order.getBox() > product.getNum()) {

            double productionCapacity;

            if (order.getProduct().equals("양배추즙")) {
                // 양배추즙 생산 가능 수량 (box)
                productionCapacity = matDTOList.get(0).getMatNum() * 2 * 0.8 / 0.08 / 30;
                System.out.println("양배추즙 생산 가능 수량(box): " + productionCapacity);
            } else if (order.getProduct().equals("흑마늘즙")) {
                // 흑마늘즙 생산 가능 수량 (box)
                productionCapacity = matDTOList.get(0).getMatNum() * 4 * 0.6 / 0.02 / 30;
                System.out.println("흑마늘즙 생산 가능 수량(box): " + productionCapacity);
            } else {
                // 석류, 매실 젤리스틱 생산 가능 수량 (box)
                productionCapacity = matDTOList.get(0).getMatNum() / 0.005 / 25;
                System.out.println("젤리스틱 생산 가능 수량(box): " + productionCapacity);
            }

            double orderCabQuantity;

            System.out.println(mat.getMatName());
            // 원자재 재고 확인
            if (order.getBox() <= productionCapacity) {
                System.out.println(mat.getMatName() + " 충분");
            } else {
                System.out.println(mat.getMatName() + " 부족");
                // 발주 수량
                if (mat.getMatName().equals("양배추")) {
                    orderCabQuantity = order.getBox() * 1.5; // 양배추즙 1box = 양배추 1.5kg
                    orderCabQuantity -= matDTOList.get(0).getMatNum(); // 수주건에 대한 원자재 필요량 - 현재 원자재 재고량
                    orderCabQuantity = Math.ceil(orderCabQuantity / 1000) * 1000; // 최소 주문량 1000kg 단위
                } else if (mat.getMatName().equals("흑마늘")) {
                    orderCabQuantity = order.getBox() * 0.25; // 흑마늘즙 1box = 흑마늘 0.25kg
                    orderCabQuantity -= matDTOList.get(0).getMatNum(); // 수주건에 대한 원자재 필요량 - 현재 원자재 재고량
                    orderCabQuantity = Math.ceil(orderCabQuantity / 10) * 10; // 최소 주문량 10kg 단위
                } else {
                    orderCabQuantity = order.getBox() * 0.125; // 젤리스틱 1box = 원자재(석류, 매실) 0.125kg
                    orderCabQuantity -= matDTOList.get(0).getMatNum(); // 수주건에 대한 원자재 필요량 - 현재 원자재 재고량
                    orderCabQuantity = Math.ceil(orderCabQuantity / 5) * 5;
                }

                // 양배추 최소 발주량 1ton
                if (mat.getMatName().equals("양배추") && orderCabQuantity <= 1000) {
                    // 발주량이 1000보다 작을 때
                    orderCabQuantity = 1000;
                    System.out.println("양배추 발주량 1000보다 작을 때");
                    System.out.println("양배추 발주량: " + orderCabQuantity + "kg");

                } else if (mat.getMatName().equals("흑마늘") && orderCabQuantity <= 10) {
                    // 흑마늘 발주량이 10보다 작을 때
                    orderCabQuantity = 10;
                    System.out.println("흑마늘 발주량 10보다 작을 때");
                    System.out.println("흑마늘 발주량: " + orderCabQuantity);
                } else if ((mat.getMatName().equals("석류") || mat.getMatName().equals("매실")) && orderCabQuantity <= 5) {
                    orderCabQuantity = 5;
                    System.out.println(mat.getMatName() + " 발주량 5보다 작을 때");
                    System.out.println(mat.getMatName() + " 발주량: " + orderCabQuantity + "kg");
                } else {
                    boolean stop = false;
                    double orderCabMax = orderCabQuantity;
                    int cnt = 1;
                    System.out.println("==========================");
                    while (!stop) {
                        if (mat.getMatName().equals("양배추") || mat.getMatName().equals("흑마늘")) {
                            if (orderCabMax > 5000) {
                                orderCabMax -= 5000;
                                System.out.println(mat.getMatName() + " 발주량: " + 5000 + "kg cnt: " + cnt);
                            } else if (0 < orderCabMax && orderCabMax <= 5000) {
                                System.out.println(mat.getMatName() + " 발주량: " + orderCabMax + "kg cnt: " + cnt);
                                orderCabMax -= orderCabMax;
                            } else {
                                stop = true;
                            }
                        } else if (mat.getMatName().equals("석류") || mat.getMatName().equals("매실") || mat.getMatName().equals("콜라겐")) {
                            if (orderCabMax > 500) {
                                orderCabMax -= 500;
                                System.out.println(mat.getMatName() + " 발주량: " + 500 + "kg cnt: " + cnt);
                            } else if (0 < orderCabMax && orderCabMax <= 500) {
                                System.out.println(mat.getMatName() + " 발주량: " + orderCabMax + "kg cnt: " + cnt);
                                orderCabMax -= orderCabMax;
                            } else {
                                stop = true;
                            }
                        }
                        cnt++;
                    }
                    System.out.println("==========================");
                }

            }


            // 입고는 월, 수, 금 오전 10:00 창고에 도착
            LocalDateTime orderTime = LocalDateTime.now(); // 원자재 발주 넣은 시간

            if (mat.getMatName().equals("양배추") || mat.getMatName().equals("흑마늘")){

                if (orderTime.getHour() < 12) {
                    // 12시 이전 주문건
                    // 1=월 ... 7=일
                    if (orderTime.getDayOfWeek().getValue() == 1 || orderTime.getDayOfWeek().getValue() == 3) {
                        // 월요일 수요일
                        cabDeliveryDate = orderTime.toLocalDate().plusDays(2).atTime(10, 0);
                        System.out.println("12시 이전 주문건 월, 수요일");
                        System.out.println("예상 입고일: " + cabDeliveryDate);
                    } else if (orderTime.getDayOfWeek().getValue() == 2 || orderTime.getDayOfWeek().getValue() == 7) {
                        // 화요일 일요일
                        cabDeliveryDate = orderTime.toLocalDate().plusDays(3).atTime(10, 0);
                        System.out.println("12시 이전 주문건 화, 일요일");
                        System.out.println("예상 입고일: " + cabDeliveryDate);
                    } else if (orderTime.getDayOfWeek().getValue() == 4 || orderTime.getDayOfWeek().getValue() == 6) {
                        // 목요일 토요일
                        cabDeliveryDate = orderTime.toLocalDate().plusDays(4).atTime(10, 0);
                        System.out.println("12시 이전 주문건 목, 토요일");
                        System.out.println("예상 입고일: " + cabDeliveryDate);
                    } else {
                        // 금요일
                        cabDeliveryDate = orderTime.toLocalDate().plusDays(5).atTime(10, 0);
                        System.out.println("12시 이전 주문건 금요일");
                        System.out.println("예상 입고일: " + cabDeliveryDate);
                    }

                } else {
                    // 12시 이후 주문건
                    if (orderTime.getDayOfWeek().getValue() == 2 || orderTime.getDayOfWeek().getValue() == 7) {
                        // 화요일 일요일
                        cabDeliveryDate = orderTime.toLocalDate().plusDays(3).atTime(10, 0);
                        System.out.println("12시 이후 주문건 화, 일요일");
                        System.out.println("예상 입고일: " + cabDeliveryDate);

                    } else if (orderTime.getDayOfWeek().getValue() == 1 || orderTime.getDayOfWeek().getValue() == 6) {
                        // 월요일 토요일
                        cabDeliveryDate = orderTime.toLocalDate().plusDays(4).atTime(10, 0);
                        System.out.println("12시 이후 주문건 월, 목요일");
                        System.out.println("예상 입고일: " + cabDeliveryDate);

                    } else if (orderTime.getDayOfWeek().getValue() == 3 || orderTime.getDayOfWeek().getValue() == 5) {
                        // 수요일 금요일
                        cabDeliveryDate = orderTime.toLocalDate().plusDays(5).atTime(10, 0);
                        System.out.println("12시 이후 주문건 수, 금요일");
                        System.out.println("예상 입고일: " + cabDeliveryDate);
                    } else {
                        // 목요일
                        cabDeliveryDate = orderTime.toLocalDate().plusDays(6).atTime(10, 0);
                        System.out.println("12시 이후 주문건 목요일");
                        System.out.println("예상 입고일: " + cabDeliveryDate);
                    }

                }
            }else if (mat.getMatName().equals("석류") || mat.getMatName().equals("매실") || mat.getMatName().equals("콜라겐")){
                if (orderTime.getHour() < 15){
                    // 15 : 00 이전 주문건
                    if(orderTime.getDayOfWeek().getValue() == 2){
                        cabDeliveryDate = orderTime.toLocalDate().plusDays(3).atTime(10, 0);
                        System.out.println("15시 이전 주문건 화요일");
                        System.out.println("예상 입고일: " + cabDeliveryDate);
                    }else if(orderTime.getDayOfWeek().getValue() == 1){
                        cabDeliveryDate = orderTime.toLocalDate().plusDays(4).atTime(10, 0);
                        System.out.println("15시 이전 주문건 월요일");
                        System.out.println("예상 입고일: " + cabDeliveryDate);
                    }else if(orderTime.getDayOfWeek().getValue() == 4 || orderTime.getDayOfWeek().getValue() == 6){
                        cabDeliveryDate = orderTime.toLocalDate().plusDays(6).atTime(10, 0);
                        System.out.println("15시 이전 주문건 목, 토요일");
                        System.out.println("예상 입고일: " + cabDeliveryDate);
                    }else{
                        cabDeliveryDate = orderTime.toLocalDate().plusDays(5).atTime(10, 00);
                        System.out.println("15시 이전 주문건 수, 금, 일요일");
                        System.out.println("예상 입고일: " + cabDeliveryDate);
                    }
                }else{
                    if (orderTime.getDayOfWeek().getValue() == 1){
                        cabDeliveryDate = orderTime.toLocalDate().plusDays(4).atTime(10, 0);
                        System.out.println("15시 이후 주문건 월요일");
                        System.out.println("예상 입고일: " + cabDeliveryDate);
                    }else if(orderTime.getDayOfWeek().getValue() == 7){
                        cabDeliveryDate = orderTime.toLocalDate().plusDays(5).atTime(10, 0);
                        System.out.println("15시 이후 주문건 일요일");
                        System.out.println("예상 입고일: " + cabDeliveryDate);
                    }else if(orderTime.getDayOfWeek().getValue() == 3 || orderTime.getDayOfWeek().getValue() == 5){
                        cabDeliveryDate = orderTime.toLocalDate().plusDays(7).atTime(10, 0);
                        System.out.println("15시 이후 주문건 수, 금요일");
                        System.out.println("예상 입고일: " + cabDeliveryDate);
                    }else{
                        cabDeliveryDate = orderTime.toLocalDate().plusDays(6).atTime(10, 0);
                        System.out.println("15시 이후 주문건 화, 목, 토요일");
                        System.out.println("예상 입고일: " + cabDeliveryDate);
                    }
                }
            }


            // 이전 발주 건 조회하고 자재발주 수량이 오버 되는지 체크 해야됨

            if (matDTOList.get(1).getMatNum() >= productionCapacity * 30) { // 재고 창고 파우치 >= 양배추 생산량의 파우치 필요
                System.out.println("포장지 충분");
            } else {
                System.out.println("포장지 부족");
                // 포장지 발주 구현
                // 이전 발주 건 조회하고 자재발주 수량이 오버 되는지 체크 해야됨
            }


        } else { // 완제품이 충분할경우
            System.out.println("출하");
            // 출하 구현
        }


    }

}
