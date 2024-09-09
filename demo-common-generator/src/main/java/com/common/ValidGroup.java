package com.common;

import javax.validation.groups.Default;

/**
 * @author diaoyn
 * @ClassName DefaultGroup
 * @Date 2024/6/24 16:06
 */
public class ValidGroup {

    public interface Insert extends Default {
    }
    public interface Update extends Default {
    }
    public interface Delete extends Default {
    }
}
