<h1 align="center">Welcome to Vanish Composable Library 👋</h1>

<p align="center">
  <a href="https://github.com/frinyvonnick/gitmoji-changelog">
    <img src="https://img.shields.io/badge/API-15%2B-blue.svg?style=flat" alt="gitmoji-changelog">
  </a>  <a href="https://github.com/frinyvonnick/gitmoji-changelog">
    <img src="https://jitpack.io/v/mejdi14/AndroidColorPicker.svg" alt="gitmoji-changelog">
  </a>
  </a>
	<a href="https://github.com/kefranabg/readme-md-generator/blob/master/LICENSE">
    <img alt="License: MIT" src="https://img.shields.io/badge/license-MIT-yellow.svg" target="_blank" />
  </a>
  <a href="https://codecov.io/gh/kefranabg/readme-md-generator">
    <img src="https://codecov.io/gh/kefranabg/readme-md-generator/branch/master/graph/badge.svg" />
  </a>
</p>

## ✨ Demo

<div style="display: flex; justify-content: center; align-items: center;">
  <img 
    src="https://raw.githubusercontent.com/mejdi14/KMP-Image-Censorship/main/demo/output.gif"
    height="400"
    width="675"
    style="margin-right: 20px;"
  />

</div>




## :art:Design inspiration

many thanks goes to [Jade Franson](https://x.com/jadefranson) for the beautiful design and
animation

## Installation

Add this to your module's `build.gradle` file (make sure the version matches the JitPack badge
above):

```gradle
dependencies {
	...
	implementation("io.github.mejdi14:kmp_image_censorship:0.1.0")
}
```

## :fire:How to use

``` java
               val painter = painterResource(resId)
               val yourPixelSize = 100
               CensorshipComposable(
                              painter,
                              modifier = modifier.align(Alignment.Center),
                              pixelSize = yourPixelSize
                                   )
```



## 🤝 Contributing

Contributions, issues and feature requests are welcome.<br />
Feel free to check [issues page] if you want to contribute.<br />

## Author

👤 **Mejdi Hafiane**

- profile: [@MejdiHafiane](https://twitter.com/mejdi141)

## Show your support

Please ⭐️ this repository if this project helped you!

## 📝 License

Copyright © 2019 [Mejdi Hafiane](https://github.com/mejdi14).<br />
This project is [MIT](https://github.com/mejdi14/readme-md-generator/blob/master/LICENSE) licensed.
