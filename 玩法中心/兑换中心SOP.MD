# 兑换中心接入文档SOP


## 兑换活动配置
创建一个兑换活动,只需要设置活动配置和奖励配置。

1. 活动配置中，需要配置活动的基础信息如scene,开始结束时间，以及活动的疲劳度，活动的疲劳度表示该活动下所有
的奖品在疲劳度度周期下可以兑换几次。疲劳度周期有day，week,all三种维度，分别表示在天维度，周维度和整个活动周期下可以兑换的次数；
2. 具体的权益配置，一个活动可以配置多个权益项，每个权益项可以进行`库存配置,疲劳度配置,兑换需要消耗用户权益配置`,
库存配置包括不限量库存和限量库存，不限量库存即表示不使用库存；限量库存支持两种方式，根据InventoryStrategyEnum选择总库存和小时波次库存。
总库存会一次性初始化库存；小时波次库存会按照设置的总库存和小时库存分批次设置库存，保证每小时都有库存可兑换。


```java
ExchangeActivityConfig config = new ExchangeActivityConfig();
config.setScene("cf_holiday");
config.setActivityName("穿越火线十一黄金周兑换得好礼");
config.setStartTime(DateUtils.formatDate("2023-10-01 00:00:00"));
config.setEndTime(DateUtils.formatDate("2023-10-07 23:59:59"));

// 活动期间只能兑换十次
ExchangeFatigueConfig activityFatigue = new ExchangeFatigueConfig();
activityFatigue.setFatigueType(FatigueType.ALL.getType());
activityFatigue.setAmount(10);


List<ExchangeBenefitConfig> benefitConfigList = new ArrayList<>();
ExchangeBenefitConfig akBenefit = new ExchangeBenefitConfig();
akBenefit.setBenefitCode("ak47");
akBenefit.setUseInventory(true);

// 库存配置,分小时分配库存
ExchangeInventoryConfig inventoryConfig = new ExchangeInventoryConfig();
inventoryConfig.setTotalAmount(200);
inventoryConfig.setHourAmount(10);
inventoryConfig.setInventoryStrategy(InventoryStrategyEnum.HOUR.getType());
akBenefit.setInventoryConfig(inventoryConfig);

// 商品疲劳度控制，周期内只能兑换1次
ExchangeFatigueConfig fatigueConfig = new ExchangeFatigueConfig();
fatigueConfig.setFatigueType(FatigueType.ALL.getType());
fatigueConfig.setAmount(1);
akBenefit.setFatigueConfig(fatigueConfig);

// 兑换消耗用户权益配置，自定义方式
ExchangeCostConfig costConfig = new ExchangeCostConfig();
costConfig.setType(ExchangeCostConfig.CostType.CUSTOM.getType());
costConfig.setAmount(1);
akBenefit.setCostConfig(costConfig);
benefitConfigList.add(akBenefit);
config.setBenefitConfigList(benefitConfigList);
```


## 