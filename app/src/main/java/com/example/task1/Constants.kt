package com.example.task1

const val DEFAULT_MASK = "# ### ## |###"
const val POLICE_MASK = "# #### |###"
const val TAXI_MASK = "## ### |###"

const val SPECIAL_CHARS = "ABEKMHOPCTYXАВЕКМНОРСТУХabekmhopctyxавекмнорстух"
const val ALL_NUMBERS = "\\d"

const val REGEX_FOUND_ANY_EXCEPT = "[^$SPECIAL_CHARS$ALL_NUMBERS]"
const val REGEX_BY_GROUPS = "[$SPECIAL_CHARS]{1,2}|$ALL_NUMBERS+"