package pl.daniel.domain.validation

import pl.daniel.domain.validation.rules.Rule
import javax.inject.Inject

class ValidateService @Inject constructor() {

    private val listOfRules: MutableList<Rule> = mutableListOf()

    fun addRule(rule: Rule): ValidateService {
        listOfRules.add(rule)
        return this
    }

    fun clear(): ValidateService {
        listOfRules.clear()
        return this
    }

    fun validate(): Boolean = listOfRules.none { it.valid().not() }
}