inline fun <ID : Any, reified Shared> Aggregate<ID>.share(
    initial: Shared,
    noinline body: (Field<ID, Shared>) -> Shared,
): Shared 