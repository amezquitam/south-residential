package com.mehill.residential;

import java.util.List;
import javax.swing.JOptionPane;
import java.time.format.DateTimeParseException;
import static java.time.LocalDate.parse;

public class App {

    private static ResidentialSystem rSystem = new ResidentialSystemImpl();

    private static void loadDefaults() {
        var apartaments = List.of(
                new Apartment("101", 1, new Owner("Harold Mendoza"), ApartmentStatus.HABITED),
                new Apartment("102", 1, new Owner("Ganyu De Jesus"), ApartmentStatus.RENTED),
                new Apartment("103", 1, new Owner("Mona China"), ApartmentStatus.HABITED),
                new Apartment("201", 2, new Owner("Shenhe De Bogotá"), ApartmentStatus.HABITED),
                new Apartment("301", 3, new Owner("Brayan Aether"), ApartmentStatus.RENTED),
                new Apartment("501", 5, new Owner("Lumine La Luna"), ApartmentStatus.HABITED),
                new Apartment("601", 6, new Owner("XianLing Quemado"), ApartmentStatus.RENTED),
                new Apartment("1001", 10, new Owner("Harold Mendoza"), ApartmentStatus.HABITED));

        apartaments.forEach(rSystem::registApartmentWithOwner);

        rSystem.pay("1001", parse("2024-04-07"));
        rSystem.pay("1001", parse("2024-05-11"));
        rSystem.pay("101", parse("2024-04-05"));
        rSystem.pay("102", parse("2024-04-12"));
        rSystem.pay("103", parse("2024-04-10"));
        rSystem.pay("201", parse("2024-04-01"));
        rSystem.pay("301", parse("2024-04-15"));
        rSystem.pay("601", parse("2024-04-30"));
    }

    public static void main(String[] args) {

        loadDefaults();

        Double percentageOfRentedApartments = rSystem.getPercentageOfRentedApartments();
        Double percentageOfEmptyApartments = rSystem.getPercentageOfEmptyApartments();

        String messageMenu = new StringBuilder()
                .append("========================================\n")
                .append("        SELECCIONE LA ACCION A REALIZAR   \n")
                .append("========================================\n")
                .append("1. Registrar apartamento y dueño\n")
                .append("2. Pagar la administración\n")
                .append("3. Obtener apartamentos por dueño\n")
                .append("4. Calcular dinero pagado en un mes especifico\n")
                .append("5. Salir\n")
                .append("Porcentaje de apartamentos alquilados: ").append(percentageOfRentedApartments).append("%\n")
                .append("Porcentaje de apartamentos vacíos: ").append(percentageOfEmptyApartments).append("%\n")
                .toString();

        do {
            String input = JOptionPane.showInputDialog(messageMenu).trim();

            if (input.equals("1"))
                registApartmentWithOwnerMenu();

            if (input.equals("2"))
                payAdminMenu();
            
            if (input.equals("3"))
                getApartmentsByOwner();
            
            if (input.equals("4"))
                calculateTotalPaymentByMonth();

            if (input.equals("5"))
                break;

        } while (true);

    }

    private static void registApartmentWithOwnerMenu() {

        Apartment apartment = new Apartment();

        try {
            String apartmentId = JOptionPane.showInputDialog("Digite el número del apartamento (3 dígitos)")
                    .trim();
            apartment.setId(apartmentId);

            String floorInput = JOptionPane
                    .showInputDialog("¿En qué piso se encuentra? Digite el número [1-10]");
            Integer floor = Integer.parseInt(floorInput);
            apartment.setFloor(floor);

            String owner = JOptionPane.showInputDialog("Ingrese el nombre del dueño");
            apartment.setOwner(new Owner(owner));

            Integer status = JOptionPane.showOptionDialog(
                    null,
                    "Seleccione el estado el apartamento",
                    "Estado del apartamento",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    ApartmentStatus.values(),
                    ApartmentStatus.HABITED);

            apartment.setStatus(ApartmentStatus.values()[status]);

            rSystem.registApartmentWithOwner(apartment);

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void payAdminMenu() {
        try {

            String apartmentId = JOptionPane.showInputDialog("Ingrese el número del apartamento");
            String payDateInput = JOptionPane.showInputDialog("Ingrese la fecha de pago en formato AAAA-MM-DD");
            var payDate = parse(payDateInput);
            rSystem.pay(apartmentId, payDate);
        } catch(DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "El formato de fecha ingresado es incorrecto", "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch(IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void getApartmentsByOwner() {
        String ownerName = JOptionPane.showInputDialog("Ingrese el nombre del dueño de los apartamentos a buscar").trim();
        var apartments = rSystem.getApartmentsByOwner(ownerName);

        if (apartments.isEmpty()) {
            JOptionPane.showMessageDialog(null, String.format("%s no tiene apartamentos", ownerName));
            return;
        }

        String apartmentList = apartments.stream().collect(
            () -> new StringBuilder(),
            (sb, id) -> sb.append(String.format(" - %s\n", id)),
            StringBuilder::append
        ).toString();

        JOptionPane.showMessageDialog(null, String.format("Apartamentos de %s\n\n%s", ownerName, apartmentList));
    }

    private static void calculateTotalPaymentByMonth() {
        try {
            String monthInput = JOptionPane.showInputDialog("Ingrese el mes en el formato AAAA-MM");
            String dateString = monthInput + "-01";
            var date = parse(dateString);
            Integer year = date.getYear();
            Integer month = date.getMonthValue();
            Double totalPaymentByMonth = rSystem.calculateTotalPaymentByMonth(year, month);
            JOptionPane.showMessageDialog(null, "Pago total del mes: " + totalPaymentByMonth);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Error en el formato de la fecha", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

}