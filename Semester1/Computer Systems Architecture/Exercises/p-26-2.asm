bits 32

global start

; declare external functions needed by our program
extern exit, fopen, fclose,scanf,printf,fprintf
import exit msvcrt.dll  
import fopen msvcrt.dll
import fclose msvcrt.dll
import scanf msvcrt.dll
import printf msvcrt.dll
import fprintf msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    file_name db "bestfile.txt", 0   ; filename to be created
    access_mode db "w", 0       ; file access mode:
                                ; w - creates an empty file for writing
    file_descriptor dd -1,0       ; variable to hold the file descriptor
    format db "%s",0
    format2 db "%c",0
    a times 100 db 0
    char dd 0
    

; our code starts here
segment code use32 class=code
    start:
        ; call fopen() to create the file
        ; fopen() will return a file descriptor in the EAX or 0 in case of error
        ; eax = fopen(file_name, access_mode)
        push dword access_mode     
        push dword file_name
        call [fopen]
        add esp, 4*2                ; clean-up the stack

        mov [file_descriptor], eax  ; store the file descriptor returned by fopen
        
        ; check if fopen() has successfully created the file (EAX != 0)
        cmp eax, 0
        je final
        
        citire_cuv:
                    push dword a
                    push dword format
                    call [scanf]  ; read string in a 
                    add esp, 4*2
                    mov eax,0
                    mov al,[a]
                    cmp al,36 ; compare string w/ $ if true end of program 
                    je final
                    
                    mov esi, 0 ; index used in comapring letters from string to see if they are upper case
                    verif_litera_mare:
                        mov al, [a+esi]
                        cmp al,0 ; check for end of string if true go to start of loop and repeat the reading 
                        je citire_cuv 
                        cmp al, 65 ; check if letter greater or equal to A
                        jge upper_bound
                        
                        jmp final_verif
                        
                        upper_bound:
                            cmp al,90 ; check if letter less or equal than Z if true print
                            jle print
                            jmp final_verif
                        print:
                            mov ebx,0
                            jmp display
                        final_verif:
                            inc esi
                    loop verif_litera_mare
        loop citire_cuv
        
        display: ; loop to display letters from a string in the file
            mov eax,[a+ebx]
            mov [char],al
            cmp al,0
            je final_display
            push dword [char]
            push dword format2
            push dword [file_descriptor]
            call [fprintf]
            add esp, 4*3
            inc ebx
        loop display
        
        final_display:  ; used to display a new line carachter
            mov eax,10
            ;mov [char], al
            push dword eax
            push dword format2
            push dword [file_descriptor]
            call [fprintf]
            add esp, 4*3
            jmp citire_cuv
        
        
        ; call fclose() to close the file
        ; fclose(file_descriptor)
        push dword [file_descriptor]
        call [fclose]
        add esp, 4
        
        final:
        
        ; exit(0)
        push dword 0      
        call [exit]