package com.ggjg.domain.exception

import java.io.IOException

class MoreDataException : RuntimeException()
class TokenErrorException : RuntimeException()
class NotFoundException : RuntimeException()
class ConflictException : RuntimeException()
class UnknownException : RuntimeException()

class NeedLoginException : IOException()