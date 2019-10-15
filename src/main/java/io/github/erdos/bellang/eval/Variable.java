package io.github.erdos.bellang.eval;

import io.github.erdos.bellang.objects.Character;
import io.github.erdos.bellang.objects.Expression;
import io.github.erdos.bellang.objects.ExpressionVisitor;
import io.github.erdos.bellang.objects.Pair;
import io.github.erdos.bellang.objects.Stream;
import io.github.erdos.bellang.objects.Symbol;

import java.util.Optional;

final class Variable {

	public static Optional<Variable> of(Expression e) {
		return e.apply(new ExpressionVisitor<Optional<Variable>>() {
			@Override
			public Optional<Variable> pair(Pair pair) {
				if (Pair.EMPTY.equals(pair.car())) {
					return Optional.of(new Variable(pair));
				} else {
					return Optional.empty();
				}
			}

			@Override
			public Optional<Variable> stream(Stream stream) {
				return Optional.empty();
			}

			@Override
			public Optional<Variable> symbol(Symbol symbol) {
				if (symbol == Symbol.T || symbol == Symbol.O || symbol == Symbol.NIL || symbol == Symbol.APPLY) {
					return Optional.empty();
				} else {
					return Optional.of(new Variable(symbol));
				}
			}

			@Override
			public Optional<Variable> character(Character character) {
				return Optional.empty();
			}
		});
	}

	private final Expression wrapped;

	private Variable(Expression wrapped) {
		assert wrapped != null;
		this.wrapped = wrapped;
	}

	public Expression getExpression() {
		return wrapped;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Variable && this.wrapped == ((Variable) o).wrapped;
	}

	@Override
	public int hashCode() {
		return wrapped.hashCode();
	}
}
