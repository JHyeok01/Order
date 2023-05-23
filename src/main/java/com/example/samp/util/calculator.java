package com.example.samp.util;

import com.example.samp.dto.MatDTO;
import com.example.samp.dto.OrdersDTO;
import com.example.samp.dto.ProductDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class calculator {

    public static void main(String[] args) {


        OrdersDTO order = new OrdersDTO();
        ProductDTO product = new ProductDTO();
        MatDTO mat = new MatDTO();
        MatDTO matPau = new MatDTO();
        MatDTO matBox = new MatDTO();
        MatDTO matCol = new MatDTO();
        MatDTO matStick = new MatDTO();
        List<MatDTO> matDTOList = new ArrayList<>();


        // 수주
        order.setId(1L);
        order.setOrderBy("테스트 주문자");
        order.setProduct("양배추즙");
        order.setBox(100);
        order.setStatus("승인");
        order.setOrderDate(LocalDateTime.now());

        // 원자재 재고
        mat.setMatName("양배추");
        mat.setMatNum(1);

        matPau.setMatName("파우치");
        matPau.setMatNum(300);

        matStick.setMatName("스틱파우치");
        matStick.setMatNum(123);

        matBox.setMatName("box");
        matBox.setMatNum(500);

        matCol.setMatName("콜라겐");
        matCol.setMatNum(123);


        matDTOList.add(mat); // 원자재
        matDTOList.add(matPau); // 파우치
        matDTOList.add(matBox); // 박스
        matDTOList.add(matCol); // 콜라겐


        // 완제품 재고
        product.setProduct(order.getProduct());
        product.setNum(90);

        System.out.println("==================================");
        System.out.println("수주 제품: " + order.getProduct());
        System.out.println("수주량: " + order.getBox());
        System.out.println("==================================");
        System.out.println("완제품 재고: " + product.getNum());

        // 생산 해야되는 수량
        int quantityToProduce = order.getBox() - product.getNum();

        // 수주량 > 완재품 재고
        if (order.getBox() > product.getNum()) {

            double productionCapacity;

            if (order.getProduct().equals("양배추즙") || order.getProduct().equals("흑마늘즙")) {
                // 양배추즙 생산 가능 수량 (box)
                if (order.getProduct().equals("양배추즙")) {
                    productionCapacity = matDTOList.get(0).getMatNum() * 2 * 0.8 / 0.08 / 30;
                    System.out.println("양배추즙 생산 가능 수량(box): " + (int) productionCapacity);
                } else {
                    // 흑마늘즙 생산 가능 수량 (box)
                    productionCapacity = matDTOList.get(0).getMatNum() * 4 * 0.6 / 0.02 / 30;
//                    productionCapacity = (productionCapacity + product.getNum()) - order.getBox();
                    System.out.println("흑마늘즙 생산 가능 수량(box): " + (int) productionCapacity);
                }

                producible(order, mat, productionCapacity, product, quantityToProduce);

                // 파우치 가진거 > 생산 해야 되는 수량 (ea)
                if (matDTOList.get(1).getMatNum() >= quantityToProduce * 30) {
                    System.out.println("파우치 충분 자재량: " + (int) matDTOList.get(1).getMatNum());
                } else {
                    // 파우치 발주
                    List<Integer> orderPau = orderVolume(order, matPau, product);
                    for (int i : orderPau) {
                        System.out.println("파우치 발주량: " + i + "ea");
                    }
                    orderDelivery(matPau);
                }
                // 박스 재고 확인
                double productionCapacityBox = order.getBox();
                producible(order, matBox, productionCapacityBox, product, quantityToProduce);

            } else {
                // 석류, 매실 젤리스틱 생산 가능 수량 (box)
                productionCapacity = matDTOList.get(0).getMatNum() / 0.005 / 25;
                System.out.println(productionCapacity);

                producible(order, mat, productionCapacity, product, quantityToProduce);

                // 콜라겐 자재 확인
                double productionCapacityCol = matDTOList.get(3).getMatNum() / 0.002 / 25;

                producible(order, matCol, productionCapacityCol, product, quantityToProduce);

                // 파우치 > 생산 가능 수량 (ea)
                if (matDTOList.get(1).getMatNum() >= quantityToProduce * 25) {
                    System.out.println("스틱 파우치 충분 자재량: " + (int) matDTOList.get(1).getMatNum());
                } else {
                    // 스틱 파우치 발주
                    List<Integer> orderStick = orderVolume(order, matStick, product);
                    for (int i : orderStick) {
                        System.out.println("스틱파우치 발주량: " + i + "ea");
                    }
                    orderDelivery(matStick);
                }
                // 박스 재고 확인
                double productionCapacityBox = order.getBox();
                producible(order, matBox, productionCapacityBox, product, quantityToProduce);

            }

            // 이전 발주 건 조회하고 자재발주 수량이 오버 되는지 체크 해야됨

        } else { // 완제품이 충분할경우
            System.out.println("출하");
            // 출하 구현
        }

        System.out.println("==================================");

    }

    public static void producible(OrdersDTO order, MatDTO mat, double productionCapacity, ProductDTO product, int quantityToProduce) {
        if (productionCapacity >= quantityToProduce) { // 여기 수정함
            System.out.println(mat.getMatName() + " 자재 충분 자재량: " + (int) mat.getMatNum());

        } else {
            List<Integer> orderMat = orderVolume(order, mat, product);
            for (int i : orderMat) {
                System.out.println(mat.getMatName() + " 발주량 " + i + "kg");
            }
            orderDelivery(mat);
        }
    }


    public static List<Integer> orderVolume(OrdersDTO order, MatDTO mat, ProductDTO product) {

        List<Integer> orderQuantityList = new ArrayList<>();

        double orderQuantity;

        switch (mat.getMatName()) {

            case "양배추":
                orderQuantity = (order.getBox() - product.getNum()) * 1.5; // 양배추즙 1box = 양배추 1.5kg

                orderQuantity -= mat.getMatNum(); // 수주건에 대한 원자재 필요량 - 현재 원자재 재고량

                orderQuantity = Math.ceil(orderQuantity / 1000) * 1000; // 최소 주문량 1000kg 단위

                break;

            case "흑마늘":
                orderQuantity = (order.getBox() - product.getNum()) * 0.25; // 흑마늘즙 1box = 흑마늘 0.25kg

                orderQuantity -= mat.getMatNum(); // 수주건에 대한 원자재 필요량 - 현재 원자재 재고량

                orderQuantity = Math.ceil(orderQuantity / 10) * 10; // 최소 주문량 10kg 단위

                break;

            case "석류":
            case "매실":
            case "콜라겐":
                orderQuantity = (order.getBox() - product.getNum()) * 0.125; // 젤리스틱 1box = 원자재(석류, 매실) 0.125kg

                orderQuantity -= mat.getMatNum(); // 수주건에 대한 원자재 필요량 - 현재 원자재 재고량

                orderQuantity = Math.ceil(orderQuantity / 5) * 5;
                break;

            case "파우치":
            case "스틱파우치":

                if (mat.getMatName().equals("파우치")) {
                    orderQuantity = (order.getBox() - product.getNum()) * 30;
                } else {
                    orderQuantity = (order.getBox() - product.getNum()) * 25;
                }
                orderQuantity -= mat.getMatNum();
                orderQuantity = Math.ceil(orderQuantity / 1000) * 1000;

                break;

            default:
                // 박스
                orderQuantity = order.getBox() - product.getNum();
                orderQuantity -= mat.getMatNum();
                orderQuantity = Math.ceil(orderQuantity / 500) * 500;
                break;
        }


        // 양배추 최소 발주량 1ton
        if (mat.getMatName().equals("양배추") && orderQuantity <= 1000) {
            // 발주량이 1000보다 작을 때
            orderQuantity = 1000;
            orderQuantityList.add((int) orderQuantity);
        } else if (mat.getMatName().equals("흑마늘") && orderQuantity <= 10) {
            // 흑마늘 발주량이 10보다 작을 때
            orderQuantity = 10;
            orderQuantityList.add((int) orderQuantity);
        } else if ((mat.getMatName().equals("석류") || mat.getMatName().equals("매실")) && orderQuantity <= 5) {
            orderQuantity = 5;
            orderQuantityList.add((int) orderQuantity);
        } else if ((mat.getMatName().equals("파우치") || mat.getMatName().equals("스틱파우치")) && orderQuantity <= 1000) {
            orderQuantity = 1000;
            orderQuantityList.add((int) orderQuantity);
        } else if (mat.getMatName().equals("box") && orderQuantity <= 500) {
            orderQuantity = 500;
            orderQuantityList.add((int) orderQuantity);
        } else {

            boolean stop = false;
            double orderMax = orderQuantity;
            while (!stop) {

                switch (mat.getMatName()) {

                    case "양배추":
                    case "흑마늘":
                        if (orderMax > 5000) {
                            orderMax -= 5000;
                            orderQuantityList.add(5000);
                        } else if (0 < orderMax) {
                            orderQuantityList.add((int) orderMax);
                            orderMax -= orderMax;
                        } else {
                            stop = true;
                        }
                        break;

                    case "석류":
                    case "매실":
                    case "콜라겐":
                        if (orderMax > 500) {
                            orderMax -= 500;
                            orderQuantityList.add(500);
                        } else if (0 < orderMax) {
                            orderQuantityList.add((int) orderMax);
                            orderMax -= orderMax;
                        } else {
                            stop = true;
                        }
                        break;

                    case "box":
                        // 박스
                        if (orderMax > 10000) {
                            orderMax -= 10000;
                            orderQuantityList.add(10000);
                        } else if (0 < orderMax) {
                            orderQuantityList.add((int) orderMax);
                            orderMax -= orderMax;
                        } else {
                            stop = true;
                        }

                        break;

                    default:
                        // 파우치 스틱
                        if (orderMax > 1000000) {
                            orderMax -= 1000000;
                            orderQuantityList.add(1000000);
                        } else if (0 < orderMax) {
                            orderQuantityList.add((int) orderMax);
                            orderMax -= orderMax;
                        } else {
                            stop = true;
                        }
                        break;
                }
            }
        }

        return orderQuantityList;
    }


    public static LocalDateTime orderDelivery(MatDTO mat) {
        // 입고는 월, 수, 금 오전 10:00 창고에 도착

        LocalDateTime orderTime = LocalDateTime.now(); // 원자재 발주 넣은 시간
        System.out.println("발주일: " + orderTime);
        LocalDateTime deliveryDate;

        if (mat.getMatName().equals("양배추") || mat.getMatName().equals("흑마늘")) {

            if (orderTime.getHour() < 12) {
                // 12시 이전 주문건
                // 1=월 ... 7=일
                deliveryDate = getLocalDateTimeBefore2Day(orderTime);

            } else {
                // 12시 이후 주문건
                deliveryDate = getLocalDateTimeAfter2Day(orderTime);

            }
        } else {
            if (orderTime.getHour() < 15) {
                // 15 : 00 이전 주문건
                if (mat.getMatName().equals("석류") || mat.getMatName().equals("매실") || mat.getMatName().equals("콜라겐")) {
                    if (orderTime.getDayOfWeek().getValue() == 2) {
                        deliveryDate = orderTime.toLocalDate().plusDays(3).atTime(10, 0);
                    } else if (orderTime.getDayOfWeek().getValue() == 1) {
                        deliveryDate = orderTime.toLocalDate().plusDays(4).atTime(10, 0);
                    } else if (orderTime.getDayOfWeek().getValue() == 4 || orderTime.getDayOfWeek().getValue() == 6) {
                        deliveryDate = orderTime.toLocalDate().plusDays(6).atTime(10, 0);
                    } else {
                        deliveryDate = orderTime.toLocalDate().plusDays(5).atTime(10, 0);
                    }

                } else {
                    // 포장지
                    deliveryDate = getLocalDateTimeBefore2Day(orderTime);
                }
            } else {
                if (mat.getMatName().equals("석류") || mat.getMatName().equals("매실") || mat.getMatName().equals("콜라겐")) {

                    if (orderTime.getDayOfWeek().getValue() == 1) {
                        deliveryDate = orderTime.toLocalDate().plusDays(4).atTime(10, 0);
                    } else if (orderTime.getDayOfWeek().getValue() == 7) {
                        deliveryDate = orderTime.toLocalDate().plusDays(5).atTime(10, 0);
                    } else if (orderTime.getDayOfWeek().getValue() == 3 || orderTime.getDayOfWeek().getValue() == 5) {
                        deliveryDate = orderTime.toLocalDate().plusDays(7).atTime(10, 0);
                    } else {
                        deliveryDate = orderTime.toLocalDate().plusDays(6).atTime(10, 0);
                    }

                } else {
                    // 포장지
                    deliveryDate = getLocalDateTimeAfter2Day(orderTime);
                }
            }

        }

        return deliveryDate;
    }

    public static LocalDateTime getLocalDateTimeAfter2Day(LocalDateTime orderTime) {
        LocalDateTime deliveryDate;
        if (orderTime.getDayOfWeek().getValue() == 2 || orderTime.getDayOfWeek().getValue() == 7) {
            // 화요일 일요일
            deliveryDate = orderTime.toLocalDate().plusDays(3).atTime(10, 0);
        } else if (orderTime.getDayOfWeek().getValue() == 1 || orderTime.getDayOfWeek().getValue() == 6) {
            // 월요일 토요일
            deliveryDate = orderTime.toLocalDate().plusDays(4).atTime(10, 0);
        } else if (orderTime.getDayOfWeek().getValue() == 3 || orderTime.getDayOfWeek().getValue() == 5) {
            // 수요일 금요일
            deliveryDate = orderTime.toLocalDate().plusDays(5).atTime(10, 0);
        } else {
            // 목요일
            deliveryDate = orderTime.toLocalDate().plusDays(6).atTime(10, 0);
        }
        return deliveryDate;
    }

    public static LocalDateTime getLocalDateTimeBefore2Day(LocalDateTime orderTime) {
        LocalDateTime deliveryDate;
        if (orderTime.getDayOfWeek().getValue() == 1 || orderTime.getDayOfWeek().getValue() == 3) {
            // 월요일 수요일
            deliveryDate = orderTime.toLocalDate().plusDays(2).atTime(10, 0);
        } else if (orderTime.getDayOfWeek().getValue() == 2 || orderTime.getDayOfWeek().getValue() == 7) {
            // 화요일 일요일
            deliveryDate = orderTime.toLocalDate().plusDays(3).atTime(10, 0);
        } else if (orderTime.getDayOfWeek().getValue() == 4 || orderTime.getDayOfWeek().getValue() == 6) {
            // 목요일 토요일
            deliveryDate = orderTime.toLocalDate().plusDays(4).atTime(10, 0);
        } else {
            // 금요일
            deliveryDate = orderTime.toLocalDate().plusDays(5).atTime(10, 0);
        }
        return deliveryDate;
    }


}
