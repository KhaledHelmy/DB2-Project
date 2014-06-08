all: clean
	mkdir config
	mkdir data
	printf "BPlusTreeN=20\nMaximumRowsCountinPage=200\nMinimumEmptyBufferSlots=1\nMaximumUsedBufferSlots=40\nLogfilePath=data/log.csv" > "config/DBApp.properties"

clean:
	rm -rf data
	rm -rf config