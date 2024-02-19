package com.solution.gdsc.ui.common

enum class AiGrade(val grade: String, val message: String) {
    SUPERIORITY("우수", "모든 것이 안전 상태입니다."),
    GENERAL("보통", "문제 현상이 발견되지 않았습니다."),
    FAULTY("불량", "불량이 감지되었습니다.")

}