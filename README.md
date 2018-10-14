# ag4s
The Silver Searcher Scala clone

- Recursive file search
- Filter search to specific paths

  ```bash
  # search for token 
  # recursively in path1 
  # only including paths that have the test
  scripts/run -G test token path1
  ```


# Development

``` bash
bloop server
scripts/run -v -G test searchParam path1 path2

# with suspended debugger
scripts/debug -v -G test searchParam path1 path2
```


