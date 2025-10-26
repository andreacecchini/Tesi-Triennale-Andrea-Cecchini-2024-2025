inline fun <ID : Any, reified Shared> Aggregate<ID>.exchange(
    initial: Shared,
    noinline body: (Field<ID, Shared>) -> Field<ID, Shared>,
): Field<ID, Shared>