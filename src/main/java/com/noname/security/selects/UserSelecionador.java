package com.noname.security.selects;

import java.util.List;

public interface UserSelecionador<T,ID> {
	public T select(List<T> objetos, ID identificador);
}
