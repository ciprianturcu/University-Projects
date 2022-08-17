%ifndef _PARITY_ASM_ ; if _PARITY_ASM_is not defined
%define _PARITY_ASM_ ; then we define it

;procedure definition
parity:
    mov ebx, [esp+4]
    test ebx,1
    jz isEven
    mov eax, 1
    jmp final
    isEven:
        mov eax,0
    final:
    ret 4
%endif
    
    
