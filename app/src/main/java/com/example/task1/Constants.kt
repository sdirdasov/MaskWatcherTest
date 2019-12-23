package com.example.task1

const val DEFAULT_MASK = "# ### ## |###"
const val POLICE_MASK = "# #### |###"
const val TAXI_MASK = "## ### |###"

const val CHOSEN_CHARS = "ABEKMHOPCTYXАВЕКМНОРСТУХabekmhopctyxавекмнорстух"
const val ALL_NUMBERS = "\\d"

const val REGEX_BY_GROUPS = "[$CHOSEN_CHARS]{1,2}|$ALL_NUMBERS+"