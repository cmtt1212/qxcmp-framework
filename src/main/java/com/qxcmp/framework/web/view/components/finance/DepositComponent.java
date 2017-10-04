package com.qxcmp.framework.web.view.components.finance;

import com.qxcmp.framework.web.view.AbstractComponent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepositComponent extends AbstractComponent {

    @Override
    public String getFragmentFile() {
        return "qxcmp/components/finance/deposit";
    }

}
