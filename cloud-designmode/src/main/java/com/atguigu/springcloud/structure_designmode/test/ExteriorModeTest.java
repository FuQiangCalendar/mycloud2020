package com.atguigu.springcloud.structure_designmode.test;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * @Package com.atguigu.springcloud.structure_designmode.test
 * @ClassName ExteriorModeTest
 * @Description 结构模型 -- 外观
 *
 * 为子系统中的一组接口提供一个一致的界面。Facade模式定义了一个高层接口，这个接口使得这一子系统更加容易使用。
 * 外观模式，即Facade，是一个比较简单的模式。它的基本思想如下：
 * 如果客户端要跟许多子系统打交道，那么客户端需要了解各个子系统的接口，比较麻烦。如果有一个统一的“中介”，让客户端只跟中介打交道，中介再去跟各个子系统打交道，对客户端来说就比较简单。所以Facade就相当于搞了一个中介。
 *
 * 我们以注册公司为例，假设注册公司需要三步：
 * 1、向工商局申请公司营业执照；
 * 2、在银行开设账户；
 * 3、在税务局开设纳税号。
 *
 * 小结：
 * Facade模式是为了给客户端提供一个统一入口，并对外屏蔽内部子系统的调用细节。
 *
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/21 13:44
 * @Version 1.0
 **/
@Slf4j
public class ExteriorModeTest {

    public static void main(String[] args) {
        test1();
    }

    public static void test1 () {
        Facade facade = new Facade();
        Company company = facade.openCompany("大强科技");
        log.info(company.toString());
    }
}

/**
* @Description : 如果子系统比较复杂，并且客户对流程也不熟悉，那就把这些流程全部委托给中介：
 * 这样，客户端只跟Facade打交道，一次完成公司注册的所有繁琐流程：
 * Company c = facade.openCompany("Facade Software Ltd.");
 *
 * 很多Web程序，内部有多个子系统提供服务，经常使用一个统一的Facade入口，例如一个RestApiController，使得外部用户调用的时候，
 * 只关心Facade提供的接口，不用管内部到底是哪个子系统处理的。
 * 更复杂的Web程序，会有多个Web服务，这个时候，经常会使用一个统一的网关入口来自动转发到不同的Web服务，
 * 这种提供统一入口的网关就是Gateway，它本质上也是一个Facade，但可以附加一些用户认证、限流限速的额外服务
 *
* @Author:FuQiangCalendar
* @Date: 2021/5/21 15:03
*/
class Facade {
    private static final AdminOfIndustry admin = new AdminOfIndustry();
    private static final Bank bank = new Bank();
    private static final Taxation taxation = new Taxation();

    public Company openCompany(String name) {
        Company c = this.admin.register(name);
        String bankAccount = this.bank.openAccount(c.getId());
        c.setBankAccount(bankAccount);
        String taxCode = this.taxation.applyTaxCode(c.getId());
        c.setTaxCode(taxCode);
        return c;
    }
}

// 工商注册:
class AdminOfIndustry {
    public Company register(String name) {
        System.out.println("1、向工商局申请公司营业执照");
        Company company = new Company();
        company.setName(name);
        company.setId("123");
        return company;
    }
}

// 银行开户:
class Bank {
    public String openAccount(String companyId) {
        System.out.println("2、在银行开设账户");
        return "123abc";
    }
}

// 纳税登记:
class Taxation {
    public String applyTaxCode(String companyId) {
        System.out.println("3、在税务局开设纳税号");
        return "123abc123";
    }
}

@Data
@ToString
class Company {
    private String name;
    private String id;
    private String bankAccount;
    private String taxCode;
}