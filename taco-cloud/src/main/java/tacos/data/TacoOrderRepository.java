package tacos.data;

import tacos.web.TacoOrder;

public interface TacoOrderRepository {
    TacoOrder save(TacoOrder order);
}
