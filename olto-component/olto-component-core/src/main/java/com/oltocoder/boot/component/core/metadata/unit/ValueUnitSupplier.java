package com.oltocoder.boot.component.core.metadata.unit;

import java.util.List;
import java.util.Optional;

public interface ValueUnitSupplier {
    /**
     * 根据ID获取单位
     *
     * @param id ID
     * @return 单位
     */
    Optional<ValueUnit> getById(String id);

    /**
     * 获取全部单位
     *
     * @return 全部单位
     */
    List<ValueUnit> getAll();
}
