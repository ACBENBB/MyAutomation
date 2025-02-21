package io.securitize.pageObjects.investorsOnboarding.securitizeId.tradingSecondaryMarket;

import io.securitize.infra.webdriver.Browser;
import io.securitize.pageObjects.AbstractPage;

public class TradingSecondaryMarketsCreateOrders extends AbstractPage {

  public TradingSecondaryMarketsCreateOrders(Browser browser) { super(browser);
  }

  public void createBuyOrder(Double orderPrice, Double orderQuantity){
    TradingSecondaryMarketsTradeModal secondaryMarketsTradeModal = new TradingSecondaryMarketsTradeModal(browser);
    secondaryMarketsTradeModal
            .setOrderPrice(orderPrice)
            .setOrderQuantity(orderQuantity)
            .clickNextButton();

    TradingSecondaryMarketsReviewTradeModal secondaryMarketsReviewTradeModal = new TradingSecondaryMarketsReviewTradeModal(browser);
    secondaryMarketsReviewTradeModal
            .clickAgreementCheckbox()
            .clickSubmitOrderButton();
    TradingSecondaryMarketsConfirmationTradeModal secondaryMarketsConfirmationTradeModal = new TradingSecondaryMarketsConfirmationTradeModal(browser);
    secondaryMarketsConfirmationTradeModal.clickGotItButton();
  }

  public void createSellOrder(Double orderPrice, Double orderQuantity){
    TradingSecondaryMarketsTradeModal secondaryMarketsTradeModal = new TradingSecondaryMarketsTradeModal(browser);
    secondaryMarketsTradeModal
            .setOrderPrice(orderPrice)
            .setOrderQuantity(orderQuantity)
            .clickNextButton();

    TradingSecondaryMarketsReviewTradeModal secondaryMarketsReviewTradeModal = new TradingSecondaryMarketsReviewTradeModal(browser);
    secondaryMarketsReviewTradeModal
            .clickAgreementCheckbox()
            .clickSubmitOrderButton();
    TradingSecondaryMarketsConfirmationTradeModal secondaryMarketsConfirmationTradeModal = new TradingSecondaryMarketsConfirmationTradeModal(browser);
    secondaryMarketsConfirmationTradeModal.clickGotItButton();

  }

}
