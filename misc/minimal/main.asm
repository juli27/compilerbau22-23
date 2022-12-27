.include "framework.asm"
.section "main"
main:

ld a, 2
add 3
call printByte

ret
.ends
