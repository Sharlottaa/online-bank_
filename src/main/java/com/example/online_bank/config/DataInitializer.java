package com.example.online_bank.config;

import com.example.online_bank.dto.*;
import com.example.online_bank.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BankService bankService;
    private final BankAtmService bankAtmService;
    private final BankOfficeService bankOfficeService;
    private final EmployeeService employeeService;
    private final UserService userService;
    private final PaymentAccountService paymentAccountService;
    private final CreditAccountService creditAccountService;

    @Autowired
    public DataInitializer(BankService bankService,
                           BankAtmService bankAtmService,
                           BankOfficeService bankOfficeService,
                           EmployeeService employeeService,
                           UserService userService,
                           PaymentAccountService paymentAccountService,
                           CreditAccountService creditAccountService) {
        this.bankService = bankService;
        this.bankAtmService = bankAtmService;
        this.bankOfficeService = bankOfficeService;
        this.employeeService = employeeService;
        this.userService = userService;
        this.paymentAccountService = paymentAccountService;
        this.creditAccountService = creditAccountService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Инициализация данных

        // Создание 5 банков
        List<BankDTO> banks = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            BankDTO bankDTO = new BankDTO();
            bankDTO.setName("Bank " + i);
            // rating и totalMoney будут сгенерированы в сервисе при создании
            banks.add(bankService.createBank(bankDTO));
        }

        // Для каждого банка создаем 3 банкомата и 3 офиса
        for (BankDTO bank : banks) {
            // Создание 3 офиса
            List<BankOfficeDTO> offices = new ArrayList<>();
            for (int j = 1; j <= 3; j++) {
                BankOfficeDTO officeDTO = new BankOfficeDTO();
                officeDTO.setName("Office " + j + " of " + bank.getName());
                officeDTO.setAddress("Address " + j + ", " + bank.getName());
                officeDTO.setStatus("работает");
                officeDTO.setCanPlaceAtm(true);
                officeDTO.setCanIssueLoans(true);
                officeDTO.setCashWithdrawalAvailable(true);
                officeDTO.setDepositAvailable(true);
                offices.add(bankOfficeService.createOffice(officeDTO.withBankId(bank.getId())));
            }

            // Создание 3 банкоматов
            for (int k = 1; k <= 3; k++) {
                BankAtmDTO atmDTO = new BankAtmDTO();
                atmDTO.setName("ATM " + k + " of " + bank.getName());
                atmDTO.setAddress("Address " + k + ", " + bank.getName());
                atmDTO.setStatus("работает");
                atmDTO.setCashWithdrawalAvailable(true);
                atmDTO.setDepositAvailable(true);
                atmDTO.setBankId(bank.getId());
                // Выбор случайного офиса для размещения банкомата
                BankOfficeDTO randomOffice = offices.get(new Random().nextInt(offices.size()));
                atmDTO.setOfficeId(randomOffice.getId());
                // Установка баланса банкомата, если необходимо
                // Например, можно установить начальный баланс
                atmDTO.setMoneyAvailable(BigDecimal.valueOf(100000)); // Пример
                bankAtmService.createAtm(atmDTO);
            }

            // Для каждого офиса создаем 5 сотрудников
            for (BankOfficeDTO office : offices) {
                for (int m = 1; m <= 5; m++) {
                    EmployeeDTO employeeDTO = new EmployeeDTO();
                    employeeDTO.setFullName("Employee " + m + " of " + office.getName());
                    employeeDTO.setBirthDate(new GregorianCalendar(1980, Calendar.JANUARY, m).getTime());
                    employeeDTO.setPosition("Position " + m);
                    employeeDTO.setRemote(false);
                    employeeDTO.setCanIssueLoans(m % 2 == 0); // Каждый второй сотрудник может выдавать кредиты
                    employeeDTO.setSalary(BigDecimal.valueOf(3000 + m * 500));
                    employeeDTO.setBankId(bank.getId());
                    employeeDTO.setBankOfficeId(office.getId());
                    employeeService.createEmployee(employeeDTO);
                }
            }

            // Создание 5 клиентов для банка
            for (int n = 1; n <= 5; n++) {
                UserDTO userDTO = new UserDTO();
                userDTO.setFullName("User " + n + " of " + bank.getName());
                userDTO.setBirthDate(new GregorianCalendar(1990, Calendar.FEBRUARY, n).getTime());
                userDTO.setPlaceOfWork("Company " + n);
                userDTO.setMonthlyIncome(BigDecimal.valueOf(1000 + n * 1000)); // от 2000 до 6000
                // Генерация кредитного рейтинга
                int income = userDTO.getMonthlyIncome().intValue();
                userDTO.setCreditRating(generateCreditRating(income));
                userDTO.setBankIds(Collections.singletonList(bank.getId()));
                userService.createUser(userDTO);
            }
        }

        // Создание 1 банка, 1 банкомата, 1 офис, 1 сотрудника, 1 клиента, 1 платежного счета, 1 кредитного счета
        BankDTO singleBank = new BankDTO();
        singleBank.setName("Ultimate Bank");
        singleBank = bankService.createBank(singleBank);

        BankOfficeDTO singleOffice = new BankOfficeDTO();
        singleOffice.setName("Main Office of " + singleBank.getName());
        singleOffice.setAddress("Main Address, " + singleBank.getName());
        singleOffice.setStatus("работает");
        singleOffice.setCanPlaceAtm(true);
        singleOffice.setCanIssueLoans(true);
        singleOffice.setCashWithdrawalAvailable(true);
        singleOffice.setDepositAvailable(true);
        singleOffice = bankOfficeService.createOffice(singleOffice.withBankId(singleBank.getId()));

        EmployeeDTO singleEmployee = new EmployeeDTO();
        singleEmployee.setFullName("John Doe");
        singleEmployee.setBirthDate(new GregorianCalendar(1985, Calendar.MARCH, 15).getTime());
        singleEmployee.setPosition("Manager");
        singleEmployee.setRemote(false);
        singleEmployee.setCanIssueLoans(true);
        singleEmployee.setSalary(BigDecimal.valueOf(5000));
        singleEmployee.setBankId(singleBank.getId());
        singleEmployee.setBankOfficeId(singleOffice.getId());
        singleEmployee = employeeService.createEmployee(singleEmployee);

        BankAtmDTO singleAtm = new BankAtmDTO();
        singleAtm.setName("Main ATM of " + singleBank.getName());
        singleAtm.setAddress("Main Address, " + singleBank.getName());
        singleAtm.setStatus("работает");
        singleAtm.setCashWithdrawalAvailable(true);
        singleAtm.setDepositAvailable(true);
        singleAtm.setBankId(singleBank.getId());
        singleAtm.setOfficeId(singleOffice.getId());
        singleAtm.setMoneyAvailable(BigDecimal.valueOf(500000)); // Пример
        bankAtmService.createAtm(singleAtm);

        UserDTO singleUser = new UserDTO();
        singleUser.setFullName("Jane Smith");
        singleUser.setBirthDate(new GregorianCalendar(1992, Calendar.APRIL, 20).getTime());
        singleUser.setPlaceOfWork("Tech Corp");
        singleUser.setMonthlyIncome(BigDecimal.valueOf(4000));
        singleUser.setCreditRating(generateCreditRating(singleUser.getMonthlyIncome().intValue()));
        singleUser.setBankIds(Collections.singletonList(singleBank.getId()));
        singleUser = userService.createUser(singleUser);

        PaymentAccountDTO singlePaymentAccount = new PaymentAccountDTO();
        singlePaymentAccount.setUserId(singleUser.getId());
        singlePaymentAccount.setBankId(singleBank.getId());
        singlePaymentAccount.setBalance(BigDecimal.ZERO); // Устанавливаем баланс
        singlePaymentAccount = paymentAccountService.createPaymentAccount(singlePaymentAccount);

        CreditAccountDTO singleCreditAccount = new CreditAccountDTO();
        singleCreditAccount.setUserId(singleUser.getId());
        singleCreditAccount.setBankId(singleBank.getId());
        singleCreditAccount.setStartDate(new GregorianCalendar(2023, Calendar.JANUARY, 1).getTime());
        singleCreditAccount.setEndDate(new GregorianCalendar(2026, Calendar.JANUARY, 1).getTime());
        singleCreditAccount.setMonths(36);
        singleCreditAccount.setCreditAmount(BigDecimal.valueOf(10000));
        singleCreditAccount.setMonthlyPayment(BigDecimal.valueOf(300));
        singleCreditAccount.setInterestRate(singleBank.getInterestRate());
        singleCreditAccount.setEmployeeId(singleEmployee.getId());
        singleCreditAccount.setPaymentAccountId(singlePaymentAccount.getId());
        singleCreditAccount = creditAccountService.createCreditAccount(singleCreditAccount);

        // Вывод созданных сущностей в консоль
        System.out.println("=== Single Entities ===");
        System.out.println("Bank: " + singleBank);
        System.out.println("Office: " + singleOffice);
        System.out.println("Employee: " + singleEmployee);
        System.out.println("ATM: " + singleAtm);
        System.out.println("User: " + singleUser);
        System.out.println("Payment Account: " + singlePaymentAccount);
        System.out.println("Credit Account: " + singleCreditAccount);

        // Дополнительно: Вывод всех данных по каждому банку
        System.out.println("\n=== All Banks Data ===");
        for (BankDTO bank : banks) {
            System.out.println("Bank: " + bank);
            // Получение банкоматов
            List<BankAtmDTO> atms = bankAtmService.getAllAtms().stream()
                    .filter(atm -> atm.getBankId() != null && atm.getBankId().equals(bank.getId()))
                    .collect(Collectors.toList());
            System.out.println("  ATMs:");
            atms.forEach(atm -> System.out.println("    " + atm));

            // Получение офисов
            List<BankOfficeDTO> offices = bankOfficeService.getAllOffices().stream()
                    .filter(office -> office.getBankId() != null && office.getBankId().equals(bank.getId()))
                    .collect(Collectors.toList());
            System.out.println("  Offices:");
            offices.forEach(office -> System.out.println("    " + office));

            // Получение сотрудников
            System.out.println("  Employees:");
            offices.forEach(office -> {
                List<EmployeeDTO> employees = employeeService.getAllEmployees().stream()
                        .filter(emp -> emp.getBankOfficeId() != null && emp.getBankOfficeId().equals(office.getId()))
                        .collect(Collectors.toList());
                employees.forEach(emp -> System.out.println("    " + emp));
            });

            // Получение клиентов
            System.out.println("  Clients:");
            List<UserDTO> clients = userService.getAllUsers().stream()
                    .filter(user -> user.getBankIds() != null && user.getBankIds().contains(bank.getId()))
                    .collect(Collectors.toList());
            clients.forEach(client -> {
                System.out.println("    " + client);
                // Получение платежных счетов клиента
                List<PaymentAccountDTO> paymentAccounts = paymentAccountService.getAllPaymentAccounts().stream()
                        .filter(acc -> acc.getUserId() != null && acc.getUserId().equals(client.getId()))
                        .collect(Collectors.toList());
                System.out.println("      Payment Accounts:");
                paymentAccounts.forEach(acc -> System.out.println("        " + acc));

                // Получение кредитных счетов клиента
                List<CreditAccountDTO> creditAccounts = creditAccountService.getAllCreditAccounts().stream()
                        .filter(acc -> acc.getUserId() != null && acc.getUserId().equals(client.getId()))
                        .collect(Collectors.toList());
                System.out.println("      Credit Accounts:");
                creditAccounts.forEach(acc -> System.out.println("        " + acc));
            });
        }
    }

    /**
     * Метод для генерации кредитного рейтинга на основе ежемесячного дохода.
     */
    private int generateCreditRating(int income) {
        if (income < 1000) {
            return 100;  // Если доход меньше 1000, рейтинг 100
        } else if (income > 10000) {
            return 1000;  // Если доход больше 10000, рейтинг 1000
        }

        // Рассчитываем рейтинг по формуле для доходов от 1000 до 10000
        return (int) (((income - 1000) / 9000.0) * 900 + 100);
    }
}
