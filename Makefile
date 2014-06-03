all: clean
	mkdir config
	mkdir data
	printf "BPlusTreeN=20\nMaximumRowsCountinPage=200\n" > "config/DBApp.properties"

clean:
	rm -rf data
	rm -rf config