# ag4s
The Silver Searcher Scala clone

- Recursive file search
- Filter search to specific paths


TODO
- Concurrent file reading
- Ignore simple gitignore files
-- https://geoff.greer.fm/2012/09/07/the-silver-searcher-adding-pthreads/
- Case insensitive search
- See if re2/j regex is faster
- Proper benchmarks
- Binary file detection



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


