package me.hawkweisman.ionesco

import scala.language.higherKinds

/**
  * An unboxed union type
  *
  * Created by Eliza on 7/21/16.
  */
trait UnboxedUnion {

  /**
    * The negation of a type `A`
    * @tparam A the type to negate
    */
  sealed trait ¬[-A]

  sealed trait TypeSet {
    type Compound[A]
    type Map[F[_]] <: TypeSet
  }
  sealed trait ∅ extends TypeSet {
    type Compound[A] = A
    type Map[F[_]] = ∅
  }

  sealed trait ∨[T <: TypeSet, H] extends TypeSet {
    // Given a type of the form `∅ ∨ A ∨ B ∨ ...` and parameter `X`, we want to produce the type
    // `¬[A] with ¬[B] with ... <:< ¬[X]`.
    type Element[X] = T#Map[¬]#Compound[¬[H]] <:< ¬[X]

    // This could be generalized as a fold, but for concision we leave it as is.
    type Compound[A] = T#Compound[H with A]

    type Map[F[_]] = T#Map[F] ∨ F[H]
  }

}