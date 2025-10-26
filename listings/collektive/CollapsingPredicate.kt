/**
 * Returns true if all elements in the collapsed field
 * satisfy the given predicate.
 */
inline fun <T> Collapse<T>.all(
    crossinline predicate: Predicate<T>
): Boolean

/**
 * Returns true if any element in the collapsed field
 * satisfies the given predicate.
 */
inline fun <T> Collapse<T>.any(
    crossinline predicate: Predicate<T>
): Boolean

/**
 * Counts how many elements in the collapsed field
 * satisfy the given predicate.
 */
inline fun <T> Collapse<T>.countMatching(
    crossinline predicate: Predicate<T>
): Int