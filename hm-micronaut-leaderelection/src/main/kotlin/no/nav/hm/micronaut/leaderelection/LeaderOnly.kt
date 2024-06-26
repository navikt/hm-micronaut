package no.nav.hm.micronaut.leaderelection

import io.micronaut.aop.Around
import io.micronaut.context.annotation.Type

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
@Around
@Type(LeaderOnlyMethodInterceptor::class)
annotation class LeaderOnly
