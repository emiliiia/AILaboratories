package org.ai.GeometricShapes;
/*
  @author emilia
  @project AI
  @class GeometricShapes
  @version 1.0.0
  @since 24.09.2023 - 9:26
*/

public class GeometricShapes {
    public static void main(String[] args) {
        // Приклади фігур
        Figure circle = new Circle(5.0);
        Figure square = new Square(4.0);
        Figure triangle = new Triangle(3.0, 4.0, 5.0);
        Figure rectangle = new Rectangle(6.0, 4.0);
        Figure rhombus = new Rhombus(5.0, 60.0);
        Figure trapezoid = new Trapezoid(5.0, 7.0, 6.0, 8.0, 4.0);

        // Приклади виклику предикатів
        System.out.println(isCircle(circle)); // Повинно вивести true
        System.out.println(isSquare(square)); // Повинно вивести true
        System.out.println(hasRightAngles(square, rectangle)); // Повинно вивести true
        System.out.println(hasEqualSides(square)); // Повинно вивести true
        // і так далі для інших предикатів
    }

    // Предикати класифікації фігур
    public static boolean isCircle(Figure figure) {
        return figure instanceof Circle;
    }

    public static boolean isSquare(Figure figure) {
        return figure instanceof Square;
    }

    public static boolean isTriangle(Figure figure) {
        return figure instanceof Triangle;
    }

    public static boolean isRectangle(Figure figure) {
        return figure instanceof Rectangle;
    }

    public static boolean isRhombus(Figure figure) {
        return figure instanceof Rhombus;
    }

    public static boolean isTrapezoid(Figure figure) {
        return figure instanceof Trapezoid;
    }

    // Предикати властивостей фігур
    public static boolean hasRightAngles(Figure... figures) {
        for (Figure figure : figures) {
            if (!(figure instanceof Rectangle) && !(figure instanceof Square)) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasEqualSides(Figure figure) {
        return figure instanceof Square || figure instanceof Rhombus || figure instanceof Triangle;
    }

    public static boolean hasSameRadius(Circle circle1, Circle circle2) {
        return Double.compare(circle1.getRadius(), circle2.getRadius()) == 0;
    }
}

// Інтерфейс для всіх геометричних фігур
interface Figure {
    double calculateArea();
    double calculatePerimeter();
}

// Приклад класу фігури - коло
class Circle implements Figure {
    private final double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }
}

class Square implements Figure {
    private final double sideLength;

    public Square(double sideLength) {
        this.sideLength = sideLength;
    }

    public double getSideLength() {
        return sideLength;
    }

    @Override
    public double calculateArea() {
        return sideLength * sideLength;
    }

    @Override
    public double calculatePerimeter() {
        return 4 * sideLength;
    }
}

class Triangle implements Figure {
    private double sideA;
    private double sideB;
    private double sideC;

    public Triangle(double sideA, double sideB, double sideC) {
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    public double getSideA() {
        return sideA;
    }

    public double getSideB() {
        return sideB;
    }

    public double getSideC() {
        return sideC;
    }

    @Override
    public double calculateArea() {
        // Використовуємо формулу Герона для розрахунку площі
        double s = (sideA + sideB + sideC) / 2;
        return Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC));
    }

    @Override
    public double calculatePerimeter() {
        return sideA + sideB + sideC;
    }

    public static boolean hasEqualSides(Triangle triangle) {
        return triangle.getSideA() == triangle.getSideB() && triangle.getSideB() == triangle.getSideC();
    }

    public static boolean hasRightAngles(Triangle triangle) {
        // Перевіряємо за теоремою Піфагора
        return (Math.pow(triangle.getSideA(), 2) + Math.pow(triangle.getSideB(), 2) == Math.pow(triangle.getSideC(), 2)) ||
                (Math.pow(triangle.getSideA(), 2) + Math.pow(triangle.getSideC(), 2) == Math.pow(triangle.getSideB(), 2)) ||
                (Math.pow(triangle.getSideB(), 2) + Math.pow(triangle.getSideC(), 2) == Math.pow(triangle.getSideA(), 2));
    }
}

class Rectangle implements Figure {
    private double length;
    private double width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    @Override
    public double calculateArea() {
        return length * width;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * (length + width);
    }

    public static boolean hasRightAngles(Rectangle rectangle) {
        return rectangle.getLength() == rectangle.getWidth();
    }

    public static boolean hasEqualSides(Rectangle rectangle) {
        return rectangle.getLength() == rectangle.getWidth();
    }
}

class Rhombus implements Figure {
    private double sideLength;
    private double angle;

    public Rhombus(double sideLength, double angle) {
        this.sideLength = sideLength;
        this.angle = angle;
    }

    public double getSideLength() {
        return sideLength;
    }

    public double getAngle() {
        return angle;
    }

    @Override
    public double calculateArea() {
        return sideLength * sideLength * Math.sin(Math.toRadians(angle));
    }

    @Override
    public double calculatePerimeter() {
        return 4 * sideLength;
    }

    public static boolean hasEqualSides(Rhombus rhombus) {
        return rhombus.getSideLength() == rhombus.getSideLength();
    }
}

class Trapezoid implements Figure {
    private double parallelSideA;
    private double parallelSideB;
    private double nonParallelSide1;
    private double nonParallelSide2;
    private double height;

    public Trapezoid(double parallelSideA, double parallelSideB, double nonParallelSide1, double nonParallelSide2, double height) {
        this.parallelSideA = parallelSideA;
        this.parallelSideB = parallelSideB;
        this.nonParallelSide1 = nonParallelSide1;
        this.nonParallelSide2 = nonParallelSide2;
        this.height = height;
    }

    public double getParallelSideA() {
        return parallelSideA;
    }

    public double getParallelSideB() {
        return parallelSideB;
    }

    public double getNonParallelSide1() {
        return nonParallelSide1;
    }

    public double getNonParallelSide2() {
        return nonParallelSide2;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public double calculateArea() {
        return (parallelSideA + parallelSideB) * height / 2.0;
    }

    @Override
    public double calculatePerimeter() {
        return parallelSideA + parallelSideB + nonParallelSide1 + nonParallelSide2;
    }

    public static boolean hasParallelSides(Trapezoid trapezoid) {
        return trapezoid.getParallelSideA() != trapezoid.getParallelSideB();
    }
}
