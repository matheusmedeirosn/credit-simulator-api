package com.creditsimulator.rest.utils;

public class RestConstants {

    public static final String API_TITLE = "API de Simulação de Empréstimo";
    public static final String API_DESCRIPTION = "API para calcular parcelas, juros e valores totais de empréstimos";
    public static final String API_VERSION = "1.0.0";
    public static final String API_CONTACT_NAME = "Equipe de Desenvolvimento";
    public static final String API_CONTACT_EMAIL = "matheusmedeiroosn@gmail.com";
    public static final String API_LICENSE = "Apache 2.0";
    public static final String API_LICENSE_URL = "https://www.apache.org/licenses/LICENSE-2.0";

    // Exemplos de entrada/saida
    public static final String EXAMPLE_AMOUNT = "10000.00";
    public static final String EXAMPLE_DATE = "1990-05-15";
    public static final String EXAMPLE_MONTHS = "12";
    public static final String EXAMPLE_ANNUAL_RATE = "0.03";
    public static final String EXAMPLE_PAYMENT_METHOD = "DEBITO_AUTOMATICO";
    public static final String EXAMPLE_UUID = "550e8400-e29b-41d4-a716-446655440000";

    // Valores minimos e maximos de entrada/saida
    public static final String MIN_VALUE_AMOUNT = "1.00";
    public static final String MIN_VALUE_MONTHS = "1";
    public static final String MAX_VALUE_MONTHS = "120";

    // Retorno saida
    public static final String MESSAGE_ERROR_VALIDATION = "Ocorreu uma falha na validação da requisição";
    public static final String VALIDATION_NOT_NULL = "O valor não deve ser nulo";
    public static final String VALIDATION_NOT_BLANK = "O valor não deve ser nulo ou vazio";
    public static final String VALIDATION_POSITIVE = "O valor deve ser positivo";
    public static final String VALIDATION_VALUE_MIN = "O valor mínimo é ";
    public static final String VALIDATION_PATTERN_DATE = "O formato da data deve ser 'yyyy-mm-dd'";





}
