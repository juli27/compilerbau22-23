gblinker := ~/uni/Compilerbau/gb-tools/Assembler/wlalink
gbas := ~/uni/Compilerbau/gb-tools/Assembler/wla-gb
gbtester := ~/uni/Compilerbau/gb-tools/Assembler/GBTester.jar
includes := ~/uni/Compilerbau/gb-tools/Assembler/

gb: main.gb

main.gb: main.o linkfile
	$(gblinker) -d -r -v -s linkfile $@ 2> wlalink-log.txt || (cat wlalink-log.txt | grep -v DISCARD && false)
	grep --color=never BANK < wlalink-log.txt 2> /dev/null
	rm wlalink-log.txt

main.o: main.asm
	$(gbas) -I $(includes) main.asm

linkfile:
	echo "[objects]" > linkfile
	echo "main.o" >> linkfile
	
test: main.gb
	java -jar $(gbtester) main.gb

clean:
	rm -f main.sym
	rm -f main.o
	rm -f wlalink-log.txt
	rm -f main.gb
	rm -f linkfile
