package core.utils;

public class TenantConfig {

    public static void setTenantConfig(Boolean isSaas, String tenantId) {
        TenantConfig.isSaas = isSaas;
        TenantConfig.tenantId = tenantId;
    }

    public static Boolean isSaas;
    public static String tenantId;
}
