# ag4s
Scala based recursive search CLI app

- Recursive file search
- Filter search to specific paths
- Pretty printing of search results
- Makes attempt at ignoring binary files
  - Anything with NUL or 0x00 byte in the file is considered binary
- [Concurrent file reading]
  - Makes use of [com.twitter.util.FuturePool](https://github.com/twitter/util/blob/master/util-core/src/main/scala/com/twitter/util/FuturePool.scala#L47) 

TODO
- Ignore simple gitignore files
- Case insensitive search
- See if re2/j regex is faster
- Proper benchmarks
- Support STDIN pipe

## 
```bash
# search for token 
# recursively in path1 
# only including paths that have the test
scripts/run -G test token path1
```

## Development

``` bash
git clone git@github.com:cevaris/ag4s.git
cd ag4s


brew install scalacenter/bloop/bloop
bloop server

scripts/run -v -G test searchParam path1 path2

# with suspended debugger
scripts/debug -v -G test searchParam path1 path2
```


