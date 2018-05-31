package com.diao.base.mapper;

import tk.mybatis.mapper.common.*;

public interface ShhMapper<T> extends BaseMapper<T>, MySqlMapper<T>, IdsMapper<T>, ExampleMapper<T>, RowBoundsMapper<T> {
}
